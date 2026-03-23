package com.viper.projects.airBnbApp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.viper.projects.airBnbApp.dto.BookingDto;
import com.viper.projects.airBnbApp.dto.BookingRequest;
import com.viper.projects.airBnbApp.entity.Booking;
import com.viper.projects.airBnbApp.entity.Hotel;
import com.viper.projects.airBnbApp.entity.Inventory;
import com.viper.projects.airBnbApp.entity.Room;
import com.viper.projects.airBnbApp.entity.User;
import com.viper.projects.airBnbApp.entity.enums.BookingStatus;
import com.viper.projects.airBnbApp.exception.ResourceNotFoundException;
import com.viper.projects.airBnbApp.repository.BookingRepository;
import com.viper.projects.airBnbApp.repository.HotelRepository;
import com.viper.projects.airBnbApp.repository.InventoryRepository;
import com.viper.projects.airBnbApp.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImple implements BookingService {
   private final BookingRepository bookingRepository;
   private final HotelRepository hotelRepository;
   private final RoomRepository roomRepository;
   private final InventoryRepository inventoryRepository;
   private final ModelMapper modelMapper;

   @Override
   @Transactional
   public BookingDto initialiseBooking(BookingRequest bookingRequest) {

      Long hotelId = bookingRequest.getHotelId();
      Long roomId = bookingRequest.getRoomId();
      LocalDate startDate = bookingRequest.getCheckInDate();
      LocalDate endDate = bookingRequest.getCheckOutDate();
      Integer roomCount = bookingRequest.getRoomsCount();
      log.info("Initialisinf booking for hotel : {} , room : {} , date {}-{} ", hotelId, roomId, startDate, endDate);

      // Get the hotel

      Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found" + bookingRequest.getHotelId()));

      Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found" + bookingRequest.getRoomId()));

      List<Inventory> inventoryList = inventoryRepository.findAndlockAvailableInventory(roomId, startDate, endDate,
            roomCount);

      Long daysCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;

      if (inventoryList.size() != daysCount) {
         throw new IllegalStateException("Room is not available anymore");

      }

      // Reserve the rooms / Update the booked count
     for (Inventory inventory : inventoryList) {

    BigDecimal newBookedCount =
            inventory.getBookedCount()
                     .add(BigDecimal.valueOf(roomCount));

    inventory.setBookedCount(newBookedCount);
}

      inventoryRepository.saveAll(inventoryList);

      // Create the booking
      User user = new User();
      user.setId(1L);

      // TODO -- get the dynamic price

      Booking booking = Booking.builder()
            .bookingStatus(BookingStatus.RESERVED)
            .hotel(hotel)
            .room(room)
            .checkInDate(startDate)
            .checkOutDate(endDate)
            .user(user)
            .roomCount(roomCount)
            .amount(BigDecimal.TEN)
            .build();

      booking = bookingRepository.save(booking);
      return modelMapper.map(booking, BookingDto.class);

   }

}
