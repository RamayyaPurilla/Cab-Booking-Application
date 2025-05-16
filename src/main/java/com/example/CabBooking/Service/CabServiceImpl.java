package com.example.CabBooking.Service;

import com.example.CabBooking.DTO.Request.CabRequest;
import com.example.CabBooking.DTO.Response.CabResponse;
import com.example.CabBooking.Exception.DriverNotFoundException;
import com.example.CabBooking.Model.Cab;
import com.example.CabBooking.Model.Driver;
import com.example.CabBooking.Repository.CabRepository;
import com.example.CabBooking.Repository.DriverRepository;
import com.example.CabBooking.Transformer.CabTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CabServiceImpl implements CabService{

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CabRepository cabRepository;


    @Autowired
    CabTransformer cabTransformer;
    @Override
    public CabResponse registerCab(CabRequest cabRequest, int driverID) {
        Optional<Driver> optionalDriver=driverRepository.findById(driverID);
        if(optionalDriver.isEmpty())
        {
            throw new DriverNotFoundException("Driver Not Found");
        }
        Driver driver=optionalDriver.get();
        Cab cab=cabTransformer.CabRequestToCab(cabRequest);
        driver.setCab(cab);
        driverRepository.save(driver);

//        cabRepository.save(cab);  //no need because we use cascade all if the parent is saved then automatically same things applied to the child

        CabResponse cabResponse=cabTransformer.CabToCabResponse(cab,driver);
        return cabResponse;
    }

    @Override
    public CabResponse getCabById(int cabId) {
        Cab cab=cabRepository.findById(cabId).orElseThrow(()-> new RuntimeException("Cab not Found"));
        Driver driver=driverRepository.getDriverByCabId(cabId);
        return cabTransformer.CabToCabResponse(cab,driver);
    }

    @Override
    public CabResponse updateCab(int cabId, CabRequest cabRequest) {
        Cab cab=cabRepository.findById(cabId).orElseThrow(()-> new RuntimeException("Cab not found"));
        cab.setCabModel(cabRequest.getCabModel());
        cab.setPerKmRate(cabRequest.getPerKmRate());
        cab.setCabNumber(cabRequest.getCabNumber());
        Cab cab1=cabRepository.save(cab);
        Driver driver=driverRepository.getDriverByCabId(cabId);
        return cabTransformer.CabToCabResponse(cab1,driver);
    }

    @Override
    public void deleteCab(int cabId) {
        Cab cab=cabRepository.findById(cabId).orElseThrow(()-> new RuntimeException("Cab not found"));

        Driver driver=driverRepository.getDriverByCabId(cabId);
        driver.setCab(null);
        driverRepository.save(driver);
        cabRepository.delete(cab);
    }

    @Override
    public List<CabResponse> getAllCabs() {
        List<Cab> cabList=cabRepository.findAll();
        List<CabResponse> cabResponsesList=new ArrayList<>();
        for(Cab cab:cabList)
        {
            Driver driver=driverRepository.getDriverByCabId(cab.getCabId());
            cabResponsesList.add(cabTransformer.CabToCabResponse(cab,driver));
        }
        return cabResponsesList;
    }

    @Override
    public List<CabResponse> getAvailableCabs() {
        List<Cab> cabList=cabRepository.findAll();
        List<CabResponse> cabResponsesList=new ArrayList<>();
        for(Cab cab:cabList)
        {
            if(cab.isAvailable())
            {
                Driver driver=driverRepository.getDriverByCabId(cab.getCabId());
                cabResponsesList.add(cabTransformer.CabToCabResponse(cab,driver));
            }
        }
        return cabResponsesList;
    }

    @Override
    public List<CabResponse> getCabsByMaxRate(double maxRate) {
        List<Cab> cabList=cabRepository.findByPerKmRateLessThan(maxRate);
        List<CabResponse> cabResponseList=new ArrayList<>();
        for(Cab cab:cabList)
        {
            Driver driver=driverRepository.getDriverByCabId(cab.getCabId());
            cabResponseList.add(cabTransformer.CabToCabResponse(cab,driver));
        }
        return cabResponseList;
    }

    @Override
    public CabResponse toggleCabAvailability(int cabId) {
        Cab cab = cabRepository.findById(cabId).orElseThrow(() -> new RuntimeException("Cab not found"));
        cab.setAvailable(!cab.isAvailable());
        Cab updated = cabRepository.save(cab);
        Driver driver=driverRepository.getDriverByCabId(cabId);
        CabResponse cabResponse=cabTransformer.CabToCabResponse(updated,driver);
        return cabResponse;
    }
}
