package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="drinks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Drink extends  Product {

    private Double capacityMilliliters;

    public Drink(){
        super.setProductType("Drink");
    }
    public Double getCapacityMilliliters() {
        return capacityMilliliters;
    }

    public void setCapacityMilliliters(Double capacityMilliliters) {
        this.capacityMilliliters = capacityMilliliters;
    }



}
