package com.accenture.tamalli.dto.products;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductPriceDTO implements Serializable {

    private Long productId;
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductPriceDTO(){

    }

    public ProductPriceDTO(Long productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }
}
