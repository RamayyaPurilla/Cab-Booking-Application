package com.example.CabBooking.Transformer;

import com.example.CabBooking.DTO.Request.CabRequest;
import com.example.CabBooking.DTO.Response.CabResponse;
import com.example.CabBooking.Model.Cab;
import com.example.CabBooking.Model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CabTransformer {

    @Autowired
    DriverTransformer driverTransformer;

    public Cab CabRequestToCab(CabRequest cabRequest) {
        Cab cab = new Cab();
        cab.setCabNumber(cabRequest.getCabNumber());
        cab.setCabModel(cabRequest.getCabModel());
        cab.setPerKmRate(cabRequest.getPerKmRate());
        return cab;
    }

    public CabResponse CabToCabResponse(Cab cab, Driver driver) {
        CabResponse cabResponse = new CabResponse();
        cabResponse.setCabModel(cab.getCabModel());
        cabResponse.setCabNumber(cab.getCabNumber());
        cabResponse.setPerKmRate(cab.getPerKmRate());
        cabResponse.setAvailable(cab.isAvailable());
        cabResponse.setDriverResponse(driverTransformer.DriverToDriverResponse(driver));
        return cabResponse;
    }
}
