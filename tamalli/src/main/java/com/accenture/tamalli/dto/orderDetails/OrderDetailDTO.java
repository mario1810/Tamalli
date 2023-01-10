package com.accenture.tamalli.dto.orderDetails;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailDTO implements Serializable {

    private Long detailOrderId;
    private int quantityOrdered;
    private String  productOrdered;
    private BigDecimal productPriceOrdered;
    private String  productLine;

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
    public OrderDetailDTO(){

    }

    public OrderDetailDTO(Long detailOrderId, int quantityOrdered, String productOrdered, BigDecimal productPriceOrdered, String productLine) {
        this.detailOrderId = detailOrderId;
        this.quantityOrdered = quantityOrdered;
        this.productOrdered = productOrdered;
        this.productPriceOrdered = productPriceOrdered;
        this.productLine = productLine;
    }
}
