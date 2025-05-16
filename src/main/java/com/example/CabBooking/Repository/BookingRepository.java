package com.example.CabBooking.Repository;

import com.example.CabBooking.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "select customer_id from booking as b where b.booking_id=:bookingId",nativeQuery = true)
    public int getcustomerIDByBookingID(@Param("bookingId") int bookigId);

    @Query(value = "select driver_id from booking as b where b.booking_id=:bookingId",nativeQuery = true)
    public int getdriverIDByBookingID(@Param("bookingId") int bookigId);


}
