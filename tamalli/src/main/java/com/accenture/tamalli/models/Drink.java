package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="drinks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Drink extends  Product {

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
