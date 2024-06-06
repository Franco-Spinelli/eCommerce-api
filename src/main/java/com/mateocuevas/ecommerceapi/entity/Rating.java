package com.mateocuevas.ecommerceapi.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class Rating {
    private double rate;
    private int count;

    public Rating() {
    }

    public Rating(double rate, int count) {
        this.rate = rate;
        this.count = count;
    }
}