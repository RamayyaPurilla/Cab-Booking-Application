package com.example.CabBooking.Service;

import com.example.CabBooking.DTO.Request.CustomerRequest;
import com.example.CabBooking.DTO.Response.CustomerResponse;
import com.example.CabBooking.Enum.Gender;
import com.example.CabBooking.Exception.CustomerNotFoundException;
import com.example.CabBooking.Model.Booking;
import com.example.CabBooking.Model.Customer;
import com.example.CabBooking.Repository.CustomerRepository;
import com.example.CabBooking.Transformer.CustomerTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer= CustomerTransformer.customerRequestToCustomer(customerRequest);
        System.out.println("debugging");

        Customer customer1=customerRepository.save(customer);

        CustomerResponse customerResponse=CustomerTransformer.CustomerToCustomerResponse(customer1);

        return customerResponse;
    }

    @Override
    public CustomerResponse getCustomer(Integer customerId) {
        Optional<Customer> optionalCustomer=customerRepository.findById(customerId);
        if(optionalCustomer.isPresent())
        {
            Customer customer=optionalCustomer.get();
            CustomerResponse customerResponse=CustomerTransformer.CustomerToCustomerResponse(customer);
            return customerResponse;
        }
        else {
            throw new CustomerNotFoundException("Customer_Not_Found");
        }
    }

    @Override
    public List<CustomerResponse> getByGender(Gender gender) {
        List<Customer> list=customerRepository.findByGender(gender);
        List<CustomerResponse> customerResponsesList=new ArrayList<>();
        for(Customer customer:list)
        {
            CustomerResponse customerResponse=CustomerTransformer.CustomerToCustomerResponse(customer);
            customerResponsesList.add(customerResponse);
        }
        return customerResponsesList;
    }

    @Override
    public List<CustomerResponse> getByAgeAndGender(Gender gender, int age) {
        List<Customer> customerList=customerRepository.findByGenderAndAge(gender,age);
        List<CustomerResponse> customerResponseList=new ArrayList<>();
        for(Customer customer:customerList)
        {
            CustomerResponse customerResponse=CustomerTransformer.CustomerToCustomerResponse(customer);
            customerResponseList.add(customerResponse);
        }
        return customerResponseList;
    }

    @Override
    public List<CustomerResponse> getAllByAgeGreaterThan(Gender gender, int age) {
        List<Customer> customerList=customerRepository.findByGenderAndAgeGreaterThan(gender,age);
        List<CustomerResponse> customerResponsesList=new ArrayList<>();
        for(Customer customer:customerList)
        {
            customerResponsesList.add(CustomerTransformer.CustomerToCustomerResponse(customer));
        }
        return customerResponsesList;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customerList=customerRepository.findAll();
        List<CustomerResponse> customerResponseList=new ArrayList<>();
        for(Customer customer:customerList)
        {
            customerResponseList.add(CustomerTransformer.CustomerToCustomerResponse(customer));
        }
        return customerResponseList;
    }

    @Override
    public CustomerResponse updateCustomer(int id, CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer=customerRepository.findById(id);
        if(optionalCustomer.isEmpty())
        {
            throw new CustomerNotFoundException("Customer Not Found");
        }
        Customer customer=optionalCustomer.get();
        customer.setGender(customerRequest.getGender());
        customer.setName(customerRequest.getName());
        customer.setEmailId(customerRequest.getEmailId());
        customer.setAge(customerRequest.getAge());
        Customer customer1=customerRepository.save(customer);
        CustomerResponse customerResponse=CustomerTransformer.CustomerToCustomerResponse(customer1);
        return customerResponse;
    }

    @Override
    public void deleteCustomer(int id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException("Customer with id " + id + " not found.");
        }
    }

    @Override
    public List<Booking> getBookingsByCustomerId(Integer customerId) {
        Optional<Customer> optionalCustomer=customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty())
        {
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found.");
        }
        Customer customer=optionalCustomer.get();
        List<Booking> bookingList=customer.getBookings();
        return bookingList;
    }

}
