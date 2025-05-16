package com.example.CabBooking.Repository;

import com.example.CabBooking.Model.Cab;
import com.example.CabBooking.Model.Customer;
import com.example.CabBooking.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CabRepository extends JpaRepository<Cab,Integer> {

    @Query(value = "SELECT * FROM cab WHERE available = true ORDER BY RAND() LIMIT 1", nativeQuery = true)
    public Cab getAvailableCabsRandomly();

    List<Cab> findByPerKmRateLessThan(double rate);


}
