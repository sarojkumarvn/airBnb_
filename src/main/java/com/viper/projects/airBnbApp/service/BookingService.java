package com.viper.projects.airBnbApp.service;

import java.util.List;


import com.viper.projects.airBnbApp.dto.BookingDto;
import com.viper.projects.airBnbApp.dto.BookingRequest;
import com.viper.projects.airBnbApp.dto.GuestDto;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequest bookingRequest);

  
    Object addGuests(Long bookingId, List<GuestDto> guestDtoList);
    


}
