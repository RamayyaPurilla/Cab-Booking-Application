package com.example.CabBooking.Service;

import com.example.CabBooking.DTO.Request.BookingRequest;
import com.example.CabBooking.DTO.Response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse bookCab(BookingRequest bookingRequest, int customerID);

    BookingResponse getBookingById(int bookingId);

    List<BookingResponse> getBookingsByCustomer(int customerId);
}
