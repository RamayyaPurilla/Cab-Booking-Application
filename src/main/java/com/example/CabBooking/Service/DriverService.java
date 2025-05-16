package com.example.CabBooking.Service;

import com.example.CabBooking.DTO.Request.DriverRequest;
import com.example.CabBooking.DTO.Response.BookingResponse;
import com.example.CabBooking.DTO.Response.DriverResponse;
import com.example.CabBooking.Model.Booking;
import com.example.CabBooking.Model.Driver;

import java.util.List;

public interface DriverService {
    DriverResponse addDriver(DriverRequest driverRequest);

    DriverResponse getDriverById(int id);

    List<DriverResponse> getAllDrivers();

    DriverResponse updateDriver(int id, DriverRequest driverRequest);

    void deleteDriver(int id);

    List<BookingResponse> getBookingsByDriverId(int driverId);
}
