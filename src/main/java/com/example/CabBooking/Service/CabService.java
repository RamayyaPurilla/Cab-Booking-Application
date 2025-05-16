package com.example.CabBooking.Service;

import com.example.CabBooking.DTO.Request.CabRequest;
import com.example.CabBooking.DTO.Response.CabResponse;
import com.example.CabBooking.Model.Cab;

import java.util.List;

public interface CabService {
    CabResponse registerCab(CabRequest cabRequest, int driverID);

    CabResponse getCabById(int cabId);

    CabResponse updateCab(int cabId, CabRequest cabRequest);

    void deleteCab(int cabId);

    List<CabResponse> getAllCabs();

    List<CabResponse> getAvailableCabs();

    List<CabResponse> getCabsByMaxRate(double maxRate);

    CabResponse toggleCabAvailability(int cabId);
}
