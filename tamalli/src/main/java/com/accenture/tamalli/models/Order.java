package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDateTime purchaseDate;
    private BigDecimal totalCost;
    private Boolean paid;

    @ManyToOne (fetch=FetchType.LAZY,cascade =CascadeType.REMOVE)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order",cascade = CascadeType.REMOVE)
    private List<OrderDetail> ordersDetail;

    public Order(){
        this.ordersDetail=new ArrayList<>();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrdersDetail() {
        return ordersDetail;
    }

    public void setOrdersDetail(List<OrderDetail> ordersDetail) {
        this.ordersDetail = ordersDetail;
    }
}
