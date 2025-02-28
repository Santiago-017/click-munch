package com.bestellen.click_munch.order;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    PENDING,
    ACCEPTED,
    PREPARING,
    DELIVERING,
    DELIVERED,
    CANCELLED;

    @JsonCreator
    public static Status fromValue(String value){
        for(Status status : values()){
            if(status.name().equalsIgnoreCase(value)){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status value: " + value);
    }


}


