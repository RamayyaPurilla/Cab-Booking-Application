package com.example.CabBooking.Repository;

import com.example.CabBooking.Enum.Gender;
import com.example.CabBooking.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findByGender(Gender gender);

    List<Customer> findByGenderAndAge(Gender gender, int age);


    List<Customer> findByGenderAndAgeGreaterThan(Gender gender, int age);
}
