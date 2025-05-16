package com.example.CabBooking.Service;

import com.example.CabBooking.DTO.Request.CustomerRequest;
import com.example.CabBooking.DTO.Response.CustomerResponse;
import com.example.CabBooking.Enum.Gender;
import com.example.CabBooking.Model.Booking;
import com.example.CabBooking.Model.Customer;

import java.util.List;

public interface CustomerService {
    public CustomerResponse createCustomer(CustomerRequest customerRequest);
    public CustomerResponse getCustomer(Integer customerId);
    public List<CustomerResponse> getByGender(Gender gender);
    public List<CustomerResponse> getByAgeAndGender(Gender gender, int age);

    List<CustomerResponse> getAllByAgeGreaterThan(Gender gender, int age);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse updateCustomer(int id, CustomerRequest customerRequest);

    void deleteCustomer(int id);

    List<Booking> getBookingsByCustomerId(Integer customerId);
}
