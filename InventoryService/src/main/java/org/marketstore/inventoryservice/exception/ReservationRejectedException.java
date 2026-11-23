package org.marketstore.inventoryservice.exception;

public class ReservationRejectedException extends RuntimeException {
    public ReservationRejectedException(String message){
        super(message);
    }
}
