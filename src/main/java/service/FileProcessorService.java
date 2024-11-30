package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import model.Customer;
import model.Sale;
import model.SaleItem;
import model.Salesperson;
import repository.CustomerRepository;
import repository.SaleRepository;
import repository.SalespersonRepository;

@Service
public class FileProcessorService {

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Scheduled(fixedRate = 10000)
    public void processFiles() {
    	
    	String inputDirectory = System.getProperty("user.home") + "/data/in";

        File dir = new File(inputDirectory);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                processFile(file);
                file.renameTo(new File(file.getAbsolutePath() + ".processed"));
            }
        }
    }

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
                return new SaleItem(
                    itemParts[0], // Item ID
                    Integer.parseInt(itemParts[1]), // Cantidad
                    new BigDecimal(itemParts[2]) // Precio
                );
            }).collect(Collectors.toList());

        Sale sale = new Sale(saleId, items,salesmanName);

        saleRepository.save(sale);
    }

}
