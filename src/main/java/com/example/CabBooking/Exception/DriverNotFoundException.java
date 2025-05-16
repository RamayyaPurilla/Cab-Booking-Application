package com.example.CabBooking.Exception;

public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException(String msg)
    {
        super(msg);
    }
}
