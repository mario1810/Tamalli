package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="tamales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tamal extends Product {

    private double weightGrams;

    public double getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(double weightGrams) {
        this.weightGrams = weightGrams;
    }
}
