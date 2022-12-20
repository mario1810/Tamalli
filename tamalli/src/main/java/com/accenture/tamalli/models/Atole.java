package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="atoles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Atole extends  Product {

    private double capacityMilliliters;

    public double getCapacityMilliliters() {
        return capacityMilliliters;
    }

    public void setCapacityMilliliters(double capacityMilliliters) {
        this.capacityMilliliters = capacityMilliliters;
    }



}
