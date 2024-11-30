package service;

import model.Sale;
import model.Salesperson;
import model.SaleItem;

import repository.CustomerRepository;
import repository.SaleRepository;
import repository.SalespersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public void generateReport() {
        // 1. Número de clientes
        long numberOfCustomers = customerRepository.count();

        // 2. Número de vendedores
        long numberOfSalespeople = salespersonRepository.count();

        // 3. ID de la venta más cara
        Optional<Sale> mostExpensiveSale = saleRepository.findAll().stream()
                .max(Comparator.comparing(this::calculateTotalSaleValue));

        // 4. Peor vendedor
        Optional<Salesperson> worstSalesperson = salespersonRepository.findAll().stream()
                .min(Comparator.comparing(this::calculateTotalSalesBySalesperson));

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
    }
}
