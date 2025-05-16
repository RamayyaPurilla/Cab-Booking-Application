package com.example.CabBooking.Controller;

import com.example.CabBooking.DTO.Request.DriverRequest;
import com.example.CabBooking.DTO.Response.DriverResponse;
import com.example.CabBooking.Service.DriverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController
{

    @Autowired
    DriverServiceImpl driverService;
    @PostMapping("/add")
    public ResponseEntity<?> addDriver(@RequestBody DriverRequest driverRequest)
    {
        try
        {
            DriverResponse driver=driverService.addDriver(driverRequest);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Internal Server Error",HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable int id) {
        try {
            DriverResponse driver = driverService.getDriverById(id);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllDrivers() {
        return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable int id, @RequestBody DriverRequest driverRequest) {
        try {
            DriverResponse updatedDriver = driverService.updateDriver(id, driverRequest);
            return new ResponseEntity<>(updatedDriver, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Driver update failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable int id) {
        try {
            driverService.deleteDriver(id);
            return new ResponseEntity<>("Driver deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getBookings/{driverId}")
    public ResponseEntity<?> getAllBookingsByDriver(@PathVariable int driverId) {
        try {
            return new ResponseEntity<>(driverService.getBookingsByDriverId(driverId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Driver not found or error occurred", HttpStatus.NOT_FOUND);
        }
    }


}
