package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="drinks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Drink extends  Product {

    private Double capacityLiters;

    public Drink(){
        super.setProductType("Drink");
    }
    public Double getCapacityLiters() {
        return capacityLiters;
    }

    public void setCapacityLiters(Double capacityLiters) {
        this.capacityLiters = capacityLiters;
    }



}
