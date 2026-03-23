package com.viper.projects.airBnbApp.service;

import com.viper.projects.airBnbApp.dto.BookingDto;
import com.viper.projects.airBnbApp.dto.BookingRequest;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequest bookingRequest);


}
