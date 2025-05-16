package com.example.CabBooking.Transformer;

import com.example.CabBooking.DTO.Request.DriverRequest;
import com.example.CabBooking.DTO.Response.DriverResponse;
import com.example.CabBooking.Model.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverTransformer {

    public DriverResponse DriverToDriverResponse(Driver driver) {
        DriverResponse driverResponse = new DriverResponse();
        driverResponse.setName(driver.getName());
        driverResponse.setAge(driver.getAge());
        driverResponse.setEmailId(driver.getEmailId());
        return driverResponse;
    }

    public Driver DriverRequestToDriver(DriverRequest driverRequest) {
        Driver driver = new Driver();
        driver.setName(driverRequest.getName());
        driver.setAge(driverRequest.getAge());
        driver.setEmailId(driverRequest.getEmailId());
        driver.setGender(driverRequest.getGender());
        return driver;
    }
}
