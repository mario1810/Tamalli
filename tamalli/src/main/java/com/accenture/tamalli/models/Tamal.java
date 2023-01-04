package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="tamales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tamal extends Product {

    private Double weightKilogram;

    public Tamal(){
        super.setProductType("Food");
    }
    public Double getWeightKilogram() {
        return weightKilogram;
    }

    public void setWeightKilogram(Double weightKilogram) {
        this.weightKilogram = weightKilogram;
    }
}
