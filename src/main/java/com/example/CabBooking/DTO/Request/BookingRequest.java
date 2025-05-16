package com.example.CabBooking.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    private String pickUp;
    private String destination;
    private double tripDistanceInKM;


    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getTripDistanceInKM() {
        return tripDistanceInKM;
    }

    public void setTripDistanceInKM(double tripDistanceInKM) {
        this.tripDistanceInKM = tripDistanceInKM;
    }

}
