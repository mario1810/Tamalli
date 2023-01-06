package com.accenture.tamalli.dto.orders;

import com.accenture.tamalli.dto.orderDetails.OrderDetailDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartDTO implements Serializable {

    private BigDecimal totalCost;
    private List<OrderDetailDTO> shoppingCartList;

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public List<OrderDetailDTO> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<OrderDetailDTO> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public  ShoppingCartDTO(){

    }

    public ShoppingCartDTO(BigDecimal totalCost, List<OrderDetailDTO> shoppingCartList) {
        this.totalCost = totalCost;
        this.shoppingCartList = shoppingCartList;
    }
}
