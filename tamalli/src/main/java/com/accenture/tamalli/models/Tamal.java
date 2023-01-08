package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="tamales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tamal extends Product {

    private double weightKilogram;

    public Tamal(){
        super.setProductType("Food");
    }
    public double getWeightKilogram() {
        return weightKilogram;
    }

    public void setWeightKilogram(double weightKilogram) {
        this.weightKilogram = weightKilogram;
    }
}
