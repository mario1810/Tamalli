package com.accenture.tamalli.dto.orderDetails;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductOrderDTO implements Serializable {

    private Long orderId;
    private Long detailOrderId;
    private Integer quantityOrdered;
    private String  productOrdered;
    private BigDecimal productPriceOrdered;
    private String  productLine;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDetailOrderId() {
        return detailOrderId;
    }

    public void setDetailOrderId(Long detailOrderId) {
        this.detailOrderId = detailOrderId;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
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
}
