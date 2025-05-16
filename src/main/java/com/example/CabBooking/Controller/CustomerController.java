package com.example.CabBooking.Controller;

import com.example.CabBooking.DTO.Request.CustomerRequest;
import com.example.CabBooking.DTO.Response.CustomerResponse;
import com.example.CabBooking.Enum.Gender;
import com.example.CabBooking.Exception.CustomerNotFoundException;
import com.example.CabBooking.Model.Booking;
import com.example.CabBooking.Service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerRequest customerRequest)
    {
        try
        {
            CustomerResponse customerResponse=customerService.createCustomer(customerRequest);
            return new ResponseEntity<>(customerResponse,HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") Integer customerId)
    {
        try
        {
            CustomerResponse customer=customerService.getCustomer(customerId);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        catch(CustomerNotFoundException ex)
        {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getByGender/{gender}")
    public ResponseEntity<?> gelAllByGender(@PathVariable("gender") Gender gender)
    {
        try
        {
            List<CustomerResponse> customerResponseList=customerService.getByGender(gender);
            return new ResponseEntity<>(customerResponseList,HttpStatus.OK);

        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Invalid gender value or error occurred", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getByGenderAndAge")
    public ResponseEntity<?> getAllByAgeAndGender(@RequestParam("gender") Gender gender, @RequestParam("AGE")
                                                  int age)
    {
        try{
            List<CustomerResponse> customerResponseList=customerService.getByAgeAndGender(gender,age);
            return new ResponseEntity<>(customerResponseList,HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>("Invalid gender value or error occurred "+ ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/getByAgeGreaterThan")
    public ResponseEntity<?> getAllByAGEGreaterThan(@RequestParam("GENDER") Gender gender,@RequestParam("age")
    int age)
    {
        try {
            List<CustomerResponse> customerResponseList=customerService.getAllByAgeGreaterThan(gender,age);
            return new ResponseEntity<>(customerResponseList,HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Invalid gender or age value or error occurred", HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse updatedCustomer = customerService.updateCustomer(id, customerRequest);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (CustomerNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        try {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } catch (CustomerNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getBookings/{id}")
    public ResponseEntity<?> getCustomerBookings(@PathVariable("id") Integer customerId) {
        try {
            List<Booking> bookings = customerService.getBookingsByCustomerId(customerId);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (CustomerNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }




}
