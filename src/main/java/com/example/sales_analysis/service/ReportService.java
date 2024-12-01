package com.example.sales_analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
import java.util.List;
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
        // 1. Número de clientes
        long numberOfCustomers = customerRepository.count();

        // 2. Número de vendedores
        long numberOfSalespeople = salespersonRepository.count();
        // 3. ID de la venta más cara
        //String mostExpensiveSale = findMostExpensiveSale();

        /*
        // 4. Peor vendedor
        Optional<Salesperson> worstSalesperson = salespersonRepository.findAll().stream()
                .min(Comparator.comparing(this::calculateTotalSalesBySalesperson));
*/
        File outputFile = new File(System.getProperty("user.home") + "/data/out/report.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Number of customers: " + numberOfCustomers + "\n");
            writer.write("Number of salespeople: " + numberOfSalespeople + "\n");
 /*           writer.write("ID of the most expensive sale: " + mostExpensiveSale + "\n");
            writer.write("Worst salesperson: " + worstSalesperson + "\n");*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Método para obtener la venta más cara
    public String findMostExpensiveSale() {
    	List<Sale> sales = saleRepository.findAll();  // Obtener todas las ventas
    	
    	String mostExpensiveSaleId = null;
    	double maxSaleValue = 0;
    	
    	// Iterar sobre las ventas y calcular el valor total de cada una
    	for (Sale sale : sales) {
    		double saleValue = sale.calculateTotalValue();
    		if (saleValue > maxSaleValue) {
    			maxSaleValue = saleValue;
    			mostExpensiveSaleId = sale.getId();
    		}
    	}
    	
    	return mostExpensiveSaleId;
    }
    /*
    
    //usar lo de jpa mejor 
    // Método para calcular el valor total de una venta
    private BigDecimal calculateTotalSaleValue(Sale sale) {
        return sale.getItems().stream()
                .map((SaleItem item) -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Método para calcular el total vendido por un vendedor
    private BigDecimal calculateTotalSalesBySalesperson(Salesperson salesperson) {
        List<Sale> salesBySalesperson = saleRepository.findBySalesmanName(salesperson.getName());
        return salesBySalesperson.stream()
                .map(this::calculateTotalSaleValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }*/
}
