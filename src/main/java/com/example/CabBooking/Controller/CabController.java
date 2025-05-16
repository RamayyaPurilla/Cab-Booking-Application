package com.example.CabBooking.Controller;

import com.example.CabBooking.DTO.Request.CabRequest;
import com.example.CabBooking.DTO.Response.CabResponse;
import com.example.CabBooking.Exception.DriverNotFoundException;
import com.example.CabBooking.Service.CabServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cab")
public class CabController {

    @Autowired
    CabServiceImpl cabService;

    @PostMapping("/register/driver/{driverId}")
    public ResponseEntity<?> cabRegister(@RequestBody CabRequest cabRequest,@PathVariable("driverId")
    int driverID)
    {
        try{
            CabResponse cabResponse = cabService.registerCab(cabRequest, driverID);
            return new ResponseEntity<>(cabResponse, HttpStatus.OK);
        }
        catch(DriverNotFoundException ex)
        {
            return new ResponseEntity<>(ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{cabId}")
    public ResponseEntity<?> getCabById(@PathVariable int cabId) {
        try {
            CabResponse cab = cabService.getCabById(cabId);
            return new ResponseEntity<>(cab, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Cab not found", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update/{cabId}")
    public ResponseEntity<?> updateCab(@PathVariable int cabId, @RequestBody CabRequest cabRequest) {
        try {
            CabResponse updatedCab = cabService.updateCab(cabId, cabRequest);
            return new ResponseEntity<>(updatedCab, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Cab update failed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{cabId}")
    public ResponseEntity<?> deleteCab(@PathVariable int cabId) {
        try {
            cabService.deleteCab(cabId);
            return new ResponseEntity<>("Cab deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Cab not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCabs() {
        return new ResponseEntity<>(cabService.getAllCabs(), HttpStatus.OK);
    }


    @GetMapping("/getAvailable")
    public ResponseEntity<?> getAvailableCabs() {
        return new ResponseEntity<>(cabService.getAvailableCabs(), HttpStatus.OK);
    }


    @GetMapping("/getByMaxRate")
    public ResponseEntity<?> getCabsByMaxRate(@RequestParam double maxRate) {
        return new ResponseEntity<>(cabService.getCabsByMaxRate(maxRate), HttpStatus.OK);
    }


    @PatchMapping("/toggleAvailability/{cabId}")
    public ResponseEntity<?> toggleAvailability(@PathVariable int cabId) {
        try {
            CabResponse cab = cabService.toggleCabAvailability(cabId);
            return new ResponseEntity<>(cab, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Cab not found or update failed", HttpStatus.BAD_REQUEST);
        }
    }


}
