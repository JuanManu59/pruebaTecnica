package com.example.sales_analysis.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Entity
@Data
public class Sale {
    @Id
    private String id;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sale_items", joinColumns = @JoinColumn(name = "sale_id"))
    private List<SaleItem> items;
    private String salesmanName;

	public Sale() {
	}

	public Sale(String id, List<SaleItem> items, String salesmanName) {
		super();
		this.id = id;
		this.items = items;
		this.salesmanName = salesmanName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<SaleItem> getItems() {
		return items;
	}
	public void setItems(List<SaleItem> items) {
		this.items = items;
	}
	public String getSalesmanName() {
		return salesmanName;
	}
	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}
	
    public double calculateTotalValue() {
        double totalValue = 0;
        for (SaleItem item : items) {
            totalValue += item.getTotal();
        }
        return totalValue;
    }
}
