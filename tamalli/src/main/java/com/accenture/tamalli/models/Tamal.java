package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="tamales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tamal extends Product {

    @NotNull
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
