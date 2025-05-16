package com.example.CabBooking.Transformer;

import com.example.CabBooking.DTO.Request.CustomerRequest;
import com.example.CabBooking.DTO.Response.CustomerResponse;
import com.example.CabBooking.Model.Customer;

public class CustomerTransformer {

    public static Customer customerRequestToCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setAge(customerRequest.getAge());
        customer.setEmailId(customerRequest.getEmailId());
        customer.setGender(customerRequest.getGender());
        return customer;
    }

    public static CustomerResponse CustomerToCustomerResponse(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setName(customer.getName());
        customerResponse.setEmailId(customer.getEmailId());
        customerResponse.setAge(customer.getAge());
        return customerResponse;
    }
}
