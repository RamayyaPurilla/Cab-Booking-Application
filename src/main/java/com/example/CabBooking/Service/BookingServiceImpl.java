package com.example.CabBooking.Service;

import com.example.CabBooking.DTO.Request.BookingRequest;
import com.example.CabBooking.DTO.Response.BookingResponse;
import com.example.CabBooking.DTO.Response.CabResponse;
import com.example.CabBooking.Exception.CabUnavailabeException;
import com.example.CabBooking.Exception.CustomerNotFoundException;
import com.example.CabBooking.Model.Booking;
import com.example.CabBooking.Model.Cab;
import com.example.CabBooking.Model.Customer;
import com.example.CabBooking.Model.Driver;
import com.example.CabBooking.Repository.BookingRepository;
import com.example.CabBooking.Repository.CabRepository;
import com.example.CabBooking.Repository.CustomerRepository;
import com.example.CabBooking.Repository.DriverRepository;
import com.example.CabBooking.Transformer.BookingTransformer;
import com.example.CabBooking.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CabServiceImpl cabService;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CabRepository cabRepository;

    @Autowired
    BookingTransformer bookingTransformer;

    @Override
    public BookingResponse bookCab(BookingRequest bookingRequest, int customerID) {
        Optional<Customer> optionalCustomer=customerRepository.findById(customerID);
        if(optionalCustomer.isEmpty())
        {
            throw new CustomerNotFoundException("Customer Not Found");
        }
        Customer customer=optionalCustomer.get();
        Cab availableCab=cabRepository.getAvailableCabsRandomly();

        if(availableCab==null) {
            throw new CabUnavailabeException("sorry! No cabs available");
        }

        Booking booking=bookingTransformer.bookingRequestToBooking(bookingRequest,availableCab.getPerKmRate());
        Booking savedBooking=bookingRepository.save(booking);

        CabResponse cabResponse=cabService.toggleCabAvailability(availableCab.getCabId());

        customer.getBookings().add(savedBooking);

        Driver driver=driverRepository.getDriverByCabId(availableCab.getCabId());

        driver.getBookings().add(savedBooking);

        Customer savedCustomer= customerRepository.save(customer);
        Driver savedDriver= driverRepository.save(driver);
//        cabRepository.save(availableCab);  //no need because driver entity has cab attribute with cascade all

        BookingResponse bookingResponse=bookingTransformer.bookingToBookingResponse(savedBooking,savedCustomer,
                availableCab, savedDriver);
        return bookingResponse;
    }

    @Override
    public BookingResponse getBookingById(int bookingId) {
        Optional<Booking> optionalBooking=bookingRepository.findById(bookingId);
        if(optionalBooking.isEmpty())throw new RuntimeException("Booking id not found");
        Booking booking=optionalBooking.get();
        int customerId=bookingRepository.getcustomerIDByBookingID(bookingId);
        int driverId=bookingRepository.getdriverIDByBookingID(bookingId);
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->  new
                RuntimeException("customer not found"));
        Driver driver=driverRepository.findById(driverId).orElseThrow(()->
                new RuntimeException("Driver not found"));
        int cabId=driverRepository.getCabByDriverId(driver.getDriverId());
        Cab cab=cabRepository.findById(cabId).orElseThrow(()-> new RuntimeException("cab not found"));
        BookingResponse bookingResponse=bookingTransformer.bookingToBookingResponse(booking,customer,cab,driver);
        return bookingResponse;
    }

    @Override
    public List<BookingResponse> getBookingsByCustomer(int customerId) {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->
                new CustomerNotFoundException("customer not found"));
        List<Booking> bookingList=customer.getBookings();
        List<BookingResponse> bookingResponseList=new ArrayList<>();
        for(Booking booking:bookingList)
        {
            int driverId=bookingRepository.getdriverIDByBookingID(booking.getBookingId());
            Driver driver=driverRepository.findById(driverId).orElseThrow(()->
                    new RuntimeException("Driver not found"));
            int cabId=driverRepository.getCabByDriverId(driver.getDriverId());
            Cab cab=cabRepository.findById(cabId).orElseThrow(()-> new RuntimeException("cab not found"));
            bookingResponseList.add(bookingTransformer.bookingToBookingResponse(booking,customer,cab,driver));
        }
        return bookingResponseList;
    }
}
