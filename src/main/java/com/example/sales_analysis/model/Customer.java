package com.example.sales_analysis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    private String id;
    private String name;
    private String business;

    public Customer() {
    }
    
    public Customer(String id, String name, String business) {
		super();
		this.id = id;
		this.name = name;
		this.business = business;
	}
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
}