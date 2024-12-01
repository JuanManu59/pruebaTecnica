package com.example.sales_analysis.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class SaleItem {
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
	
	public double getTotal() {
        return price * quantity;
    }
}
