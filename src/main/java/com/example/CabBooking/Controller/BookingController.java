package com.example.CabBooking.Controller;

import com.example.CabBooking.DTO.Request.BookingRequest;
import com.example.CabBooking.DTO.Response.BookingResponse;
import com.example.CabBooking.Exception.CabUnavailabeException;
import com.example.CabBooking.Exception.CustomerNotFoundException;
import com.example.CabBooking.Service.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingServiceImpl bookingService;

    @PostMapping("/cabBook/{customerId}")
    public ResponseEntity<?> cabBooking(@RequestBody BookingRequest bookingRequest, @PathVariable("customerId")
                                        int customerID)
    {
        try
        {
            BookingResponse bookingResponse=bookingService.bookCab(bookingRequest,customerID);
            return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
        }
        catch(CustomerNotFoundException ex)
        {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (CabUnavailabeException ex)
        {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBooking(@PathVariable int bookingId) {
        try {
            BookingResponse bookingResponse = bookingService.getBookingById(bookingId);
            return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getBookingsByCustomer(@PathVariable int customerId) {
        try {
            List<BookingResponse> bookings = bookingService.getBookingsByCustomer(customerId);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Customer or bookings not found", HttpStatus.NOT_FOUND);
        }
    }



}
