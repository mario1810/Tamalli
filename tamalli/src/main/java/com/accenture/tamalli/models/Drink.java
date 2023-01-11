package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="drinks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Drink extends  Product {

    @NotNull
    private double capacityLiters;

    public Drink(){
        super.setProductType("Drink");
    }
    public double getCapacityLiters() {
        return capacityLiters;
    }

    public void setCapacityLiters(double capacityLiters) {
        this.capacityLiters = capacityLiters;
    }



}
