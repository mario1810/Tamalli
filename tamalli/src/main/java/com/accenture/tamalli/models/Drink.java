package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="drinks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Drink extends  Product {

    private double capacityMilliliters;

    public Drink(){
        super.setProductType("Drink");
    }
    public double getCapacityMilliliters() {
        return capacityMilliliters;
    }

    public void setCapacityMilliliters(double capacityMilliliters) {
        this.capacityMilliliters = capacityMilliliters;
    }



}
