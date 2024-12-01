package com.example.sales_analysis.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Salesperson {
    @Id
    private String id;
    private String name;
    private BigDecimal salary;
    
    public Salesperson() {
    }
    
    public Salesperson(String id, String name, BigDecimal salary) {
    	super();
    	this.id = id;
    	this.name = name;
    	this.salary = salary;
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
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
    
}
