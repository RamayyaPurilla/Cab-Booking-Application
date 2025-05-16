package com.example.CabBooking.Repository;

import com.example.CabBooking.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {

    @Query(value = "select * from driver_info where cab_id=:cabId",nativeQuery = true)
    Driver getDriverByCabId(@Param("cabId") int cabId);

    @Query(value = "select cab_id from driver_info where driver_id=driverId",nativeQuery = true)
    int getCabByDriverId(@Param("driverId") int driverId);
}
