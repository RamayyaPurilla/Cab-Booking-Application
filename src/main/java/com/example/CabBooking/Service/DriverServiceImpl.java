package com.example.CabBooking.Service;

import com.example.CabBooking.DTO.Request.DriverRequest;
import com.example.CabBooking.DTO.Response.BookingResponse;
import com.example.CabBooking.DTO.Response.DriverResponse;
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
import com.example.CabBooking.Transformer.DriverTransformer;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService{

    @Autowired
    DriverRepository driverRepository;
    @Autowired
    DriverTransformer driverTransformer;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingTransformer bookingTransformer;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CabRepository cabRepository;
    @Override
    public DriverResponse addDriver(DriverRequest driverRequest) {
        Driver driver=driverTransformer.DriverRequestToDriver(driverRequest);

        driverRepository.save(driver);

        DriverResponse driverResponse=driverTransformer.DriverToDriverResponse(driver);
        return driverResponse;
    }

    @Override
    public DriverResponse getDriverById(int id) {
        Optional<Driver> optionalDriver=driverRepository.findById(id);
        if(optionalDriver.isEmpty())
        {
            throw new RuntimeException("Driver Not registered");
        }
        Driver driver=optionalDriver.get();
        DriverResponse driverResponse=driverTransformer.DriverToDriverResponse(driver);
        return driverResponse;
    }

    @Override
    public List<DriverResponse> getAllDrivers() {
        List<Driver> driverList=driverRepository.findAll();
        List<DriverResponse> driverResponsesList=new ArrayList<>();
        for(Driver driver:driverList)
        {
            driverResponsesList.add(driverTransformer.DriverToDriverResponse(driver));
        }
        return driverResponsesList;
    }

    @Override
    public DriverResponse updateDriver(int id, DriverRequest request) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setName(request.getName());
        driver.setAge(request.getAge());
        driver.setEmailId(request.getEmailId());
        driver.setGender(request.getGender());
        driverRepository.save(driver);
        return driverTransformer.DriverToDriverResponse(driver);
    }

    @Override
    public void deleteDriver(int id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        driverRepository.delete(driver);
    }

    @Override
    public List<BookingResponse> getBookingsByDriverId(int driverId) {
        Driver driver=driverRepository.findById(driverId).orElseThrow(()-> new
                RuntimeException("Driver Not found"));
        List<Booking> bookingList=driver.getBookings();
        List<BookingResponse> bookingResponseList=new ArrayList<>();
        for(Booking booking:bookingList)
        {
            int customerId=bookingRepository.getcustomerIDByBookingID(booking.getBookingId());
            Customer customer=customerRepository.findById(customerId).orElseThrow(()->  new
                    RuntimeException("customer not found"));
            int cabId=driverRepository.getCabByDriverId(driver.getDriverId());
            Cab cab=cabRepository.findById(cabId).orElseThrow(()-> new RuntimeException("cab not found"));
            bookingResponseList.add(bookingTransformer.bookingToBookingResponse(booking,customer,cab,driver));
        }
        return bookingResponseList;
    }
}
