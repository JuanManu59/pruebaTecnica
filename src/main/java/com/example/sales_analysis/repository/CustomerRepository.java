package com.example.sales_analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sales_analysis.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
