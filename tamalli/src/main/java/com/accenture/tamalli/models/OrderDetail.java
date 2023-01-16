package com.accenture.tamalli.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="orderDetails")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public final class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailOrderId;

    private int quantityOrdered;
    private String  productOrdered;
    private BigDecimal productPriceOrdered;
    private String  productLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orderId")
    private  Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productId")
    private Product product;

    public Long getDetailOrderId() {
        return detailOrderId;
    }

    public void setDetailOrderId(Long detailOrderId) {
        this.detailOrderId = detailOrderId;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public String getProductOrdered() {
        return productOrdered;
    }

    public void setProductOrdered(String productOrdered) {
        this.productOrdered = productOrdered;
    }

    public BigDecimal getProductPriceOrdered() {
        return productPriceOrdered;
    }

    public void setProductPriceOrdered(BigDecimal productPriceOrdered) {
        this.productPriceOrdered = productPriceOrdered;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
