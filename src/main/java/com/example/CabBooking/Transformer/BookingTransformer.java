package com.example.CabBooking.Transformer;

import com.example.CabBooking.DTO.Request.BookingRequest;
import com.example.CabBooking.DTO.Response.BookingResponse;
import com.example.CabBooking.DTO.Response.CustomerResponse;
import com.example.CabBooking.Model.Booking;
import com.example.CabBooking.Model.Cab;
import com.example.CabBooking.Model.Customer;
import com.example.CabBooking.Model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingTransformer {

    @Autowired
    CabTransformer cabTransformer;

    public Booking bookingRequestToBooking(BookingRequest bookingRequest, double perKmRate) {
        Booking booking = new Booking();
        booking.setPickUp(bookingRequest.getPickUp());
        booking.setDestination(bookingRequest.getDestination());
        booking.setTripDistanceInKM(bookingRequest.getTripDistanceInKM());
        booking.setTripStatus("In_progress");
        booking.setBillAmount(perKmRate * bookingRequest.getTripDistanceInKM());
        return booking;
    }

    public BookingResponse bookingToBookingResponse(Booking booking, Customer customer,
                                                    Cab cab, Driver driver) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setPickUp(booking.getPickUp());
        bookingResponse.setDestination(booking.getDestination());
        bookingResponse.setTripDistanceInKM(booking.getTripDistanceInKM());
        bookingResponse.setTripStatus(booking.getTripStatus());
        bookingResponse.setBillAmount(booking.getBillAmount());
        bookingResponse.setBookAt(booking.getBookAt());
        bookingResponse.setLastUpdatedAt(booking.getLastUpdatedAt());
        bookingResponse.setCustomerResponse(CustomerTransformer.CustomerToCustomerResponse(customer));
        bookingResponse.setCabResponse(cabTransformer.CabToCabResponse(cab, driver));
        return bookingResponse;
    }
}
