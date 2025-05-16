package com.example.CabBooking.Exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String msg)
    {
        super(msg);
    }
}
