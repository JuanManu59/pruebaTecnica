package com.example.sales_analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sales_analysis.model.Sale;
import com.example.sales_analysis.model.SaleItem;
import com.example.sales_analysis.model.Salesperson;
import com.example.sales_analysis.repository.CustomerRepository;
import com.example.sales_analysis.repository.SaleRepository;
import com.example.sales_analysis.repository.SalespersonRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired
    private SaleRepository saleRepository;
    
    @Scheduled(fixedRate = 10000)
    public void generateReport() {
        long numberOfCustomers = customerRepository.count();

        long numberOfSalespeople = salespersonRepository.count();
        
        String mostExpensiveSale = findMostExpensiveSale();

        String worstSalesperson = findWorstSalesperson();

        File outputFile = new File(System.getProperty("user.home") + "/data/out/report.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Number of customers: " + numberOfCustomers + "\n");
            writer.write("Number of salespeople: " + numberOfSalespeople + "\n");
            writer.write("ID of the most expensive sale: " + mostExpensiveSale + "\n");
            writer.write("Worst salesperson: " + worstSalesperson + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public String findMostExpensiveSale() {
    	List<Sale> sales = saleRepository.findAll(); 
    	
    	String mostExpensiveSaleId = null;
    	double maxSaleValue = 0;
    	
    	for (Sale sale : sales) {
    		double saleValue = sale.calculateTotalValue();
    		if (saleValue > maxSaleValue) {
    			maxSaleValue = saleValue;
    			mostExpensiveSaleId = sale.getId();
    		}
    	}
    	
    	return mostExpensiveSaleId;
    }
    
    public String findWorstSalesperson() {
        List<Sale> sales = saleRepository.findAll(); 
        Map<String, Double> salespersonSales = new HashMap<>();

        for (Sale sale : sales) {
            double totalSaleValue = sale.calculateTotalValue();
            String salesmanName = sale.getSalesmanName();
            
            salespersonSales.put(salesmanName, salespersonSales.getOrDefault(salesmanName, 0.0) + totalSaleValue);
        }

        String worstSalesperson = null;
        double minSalesValue = Double.MAX_VALUE;
        
        for (Map.Entry<String, Double> entry : salespersonSales.entrySet()) {
            if (entry.getValue() < minSalesValue) {
                minSalesValue = entry.getValue();
                worstSalesperson = entry.getKey();
            }
        }

        return worstSalesperson != null ? worstSalesperson : "N/A";
    }

}
