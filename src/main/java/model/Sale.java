package model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Sale {
    @Id
    private String id;
    @ElementCollection
    private List<SaleItem> items;
    private String salesmanName;

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
}
