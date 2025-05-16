package com.example.CabBooking.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private String pickUp;
    private String destination;
    private double tripDistanceInKM;
    private String tripStatus;
    private double billAmount;

    private Date bookAt;

    private Date lastUpdatedAt;
    private CustomerResponse customerResponse;
    private CabResponse cabResponse;

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

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public Date getBookAt() {
        return bookAt;
    }

    public void setBookAt(Date bookAt) {
        this.bookAt = bookAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public CustomerResponse getCustomerResponse() {
        return customerResponse;
    }

    public void setCustomerResponse(CustomerResponse customerResponse) {
        this.customerResponse = customerResponse;
    }

    public CabResponse getCabResponse() {
        return cabResponse;
    }

    public void setCabResponse(CabResponse cabResponse) {
        this.cabResponse = cabResponse;
    }


}
