package model;

import java.math.BigDecimal;

public class SaleItem {
    private String itemId;
    private Integer quantity;
    private BigDecimal price;
    
    public SaleItem(String itemId, Integer quantity, BigDecimal price) {
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
