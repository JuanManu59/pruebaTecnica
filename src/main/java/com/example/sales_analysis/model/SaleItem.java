package com.example.sales_analysis.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SaleItem {
	@Id
    private String itemId;
    private Integer quantity;
    private double price;
    
    public SaleItem() {
	}
    
    public SaleItem(String itemId, Integer quantity, double price) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	}

    public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}