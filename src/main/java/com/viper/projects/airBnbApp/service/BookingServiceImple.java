package com.viper.projects.airBnbApp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.dto.BookingDto;
import com.viper.projects.airBnbApp.dto.BookingRequest;
import com.viper.projects.airBnbApp.dto.GuestDto;
import com.viper.projects.airBnbApp.entity.Booking;
import com.viper.projects.airBnbApp.entity.Guest;
import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.Inventory;
import com.viper.projects.airBnbApp.entity.Room;
import com.viper.projects.airBnbApp.entity.User;
import com.viper.projects.airBnbApp.entity.enums.BookingStatus;
import com.viper.projects.airBnbApp.exception.ResourceNotFoundException;
import com.viper.projects.airBnbApp.repository.BookingRepository;
import com.viper.projects.airBnbApp.repository.GuestRepository;
import com.viper.projects.airBnbApp.repository.HotelRepository;
import com.viper.projects.airBnbApp.repository.InventoryRepository;
import com.viper.projects.airBnbApp.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookingServiceImple implements BookingService {

      private final BookingRepository bookingRepository;
      private final HotelRepository hotelRepository;
      private final RoomRepository roomRepository;
      private final InventoryRepository inventoryRepository;
      private final ModelMapper modelMapper;
      private final GuestRepository guestRepository ;

      @Override
      public BookingDto initialiseBooking(BookingRequest bookingRequest) {

            Long hotelId = bookingRequest.getHotelId();
            Long roomId = bookingRequest.getRoomId();
            LocalDate startDate = bookingRequest.getCheckInDate();
            LocalDate endDate = bookingRequest.getCheckOutDate();
            Integer roomCount = bookingRequest.getRoomsCount();

            log.info("Booking init → hotel {}, room {}, {} - {}",
                        hotelId, roomId, startDate, endDate);

            Hotel hotel = hotelRepository.findById(hotelId)
                        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

            Room room = roomRepository.findById(roomId)
                        .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

            // ⭐ checkout is exclusive
            Long daysCount = ChronoUnit.DAYS.between(startDate, endDate);

            List<Inventory> inventoryList = inventoryRepository.findAndlockAvailableInventory(
                        roomId,
                        startDate,
                        endDate.minusDays(1),
                        roomCount);

            log.info("Inventory rows fetched = {}", inventoryList.size());

            if (inventoryList.size() != daysCount) {
                  throw new IllegalStateException("Room not available for full duration");
            }

            // ⭐ reserve inventory
            for (Inventory inventory : inventoryList) {

                  int newBooked = inventory.getBookedCount() + roomCount;

                  if (newBooked > inventory.getTotalCount()) {
                        throw new IllegalStateException("Inventory exhausted");
                  }

                  inventory.setBookedCount(newBooked);
            }

            inventoryRepository.saveAll(inventoryList);
            inventoryRepository.flush();

         
  

            Booking booking = Booking.builder()
                        .bookingStatus(BookingStatus.RESERVED)
                        .hotel(hotel)
                        .room(room)
                        .checkInDate(startDate)
                        .checkOutDate(endDate)
                        .roomCount(roomCount)
                        .user(getCurrentUser())
                        .amount(room.getBasePrice()
                                    .multiply(BigDecimal.valueOf(roomCount))
                                    .multiply(BigDecimal.valueOf(daysCount)))
                        .build();

            booking = bookingRepository.save(booking);

            return modelMapper.map(booking, BookingDto.class);
      }

      @Override
      public Object addGuests(Long bookingId, List<GuestDto> guestDtoList) {

        

            log.info("Adding guests for booking with ID : {}" + bookingId);

            Booking booking = bookingRepository
            .findById(bookingId)
            .orElseThrow(()->
             new ResourceNotFoundException
             ("Hotel not found with the ID : {}" + bookingId));

             if(hasBookingExpired(booking)) {
                  throw new IllegalStateException("Booking has alreadty expired ");
             }

             if(booking.getBookingStatus() != BookingStatus.RESERVED) {
                  throw new IllegalStateException("Booking his not under reserved state , can not add guests ");
                  
             }

             for( GuestDto guestDto : guestDtoList) {
                  Guest guest = modelMapper.map(guestDto, Guest.class );
                  guest.setUser(getCurrentUser());
                  guest = guestRepository.save(guest);
                  booking.getGuests().add(guest);

                  
             }
             booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
             booking = bookingRepository.save(booking);


            return modelMapper.map(booking, BookingDto.class );
            
      }




      public boolean hasBookingExpired(Booking booking) {
            return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());

      }

      public User getCurrentUser() {
            User user = new User();
            user.setId(1L);
            return user ;
      }


      // TODO : Delete or Modify the guest before booking ! 

      
}