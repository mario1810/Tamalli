package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="tamales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tamal extends Product {

    private Double weightGrams;

    public Tamal(){
        super.setProductType("Food");
    }
    public Double getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(Double weightGrams) {
        this.weightGrams = weightGrams;
    }
}
