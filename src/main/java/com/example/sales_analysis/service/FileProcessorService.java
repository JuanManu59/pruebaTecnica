package com.example.sales_analysis.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.sales_analysis.model.Customer;
import com.example.sales_analysis.model.Sale;
import com.example.sales_analysis.model.SaleItem;
import com.example.sales_analysis.model.Salesperson;
import com.example.sales_analysis.repository.CustomerRepository;
import com.example.sales_analysis.repository.ItemRepository;
import com.example.sales_analysis.repository.SaleRepository;
import com.example.sales_analysis.repository.SalespersonRepository;

import jakarta.transaction.Transactional;

@Service
public class FileProcessorService {

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ReportService reportService;
    
    @Scheduled(fixedRate = 10000)
    public void processFiles1() {
    	
    	String inputDirectory = System.getProperty("user.home") + "/data/in";

        File dir = new File(inputDirectory);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                processFile(file);
                file.renameTo(new File(file.getAbsolutePath() + ".processed"));
            }
        }
        
        reportService.generateReport();
    }
    
    @Transactional
    private void processFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line) {
        String[] parts = line.split("ç");
        switch (parts[0]) {
            case "001":
                Salesperson salesperson = new Salesperson(parts[1], parts[2], new BigDecimal(parts[3]));
                salespersonRepository.save(salesperson);
                break;
            case "002":
                Customer customer = new Customer(parts[1], parts[2], parts[3]);
                customerRepository.save(customer);
                break;
            case "003":
            	processSale(parts);
                break;
        }
    }
    
    private void processSale(String[] parts) {
        String saleId = parts[1];
        String itemsData = parts[2].replace("[", "").replace("]", ""); 
        String salesmanName = parts[3];

        List<SaleItem> items = Arrays.stream(itemsData.split(","))
            .map(item -> {
                String[] itemParts = item.split("-");
                
                SaleItem newSaleItem =new SaleItem(
                    itemParts[0], // Item ID
                    Integer.parseInt(itemParts[1]), // Cantidad
                    Double.parseDouble(itemParts[2]) // Precio
                );
                itemRepository.save(newSaleItem);                
                return newSaleItem;
            }).collect(Collectors.toList());

        Sale sale = new Sale(saleId, items,salesmanName);

        saleRepository.save(sale);
    }

}
