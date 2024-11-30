package repository;

import model.Sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaleRepository extends JpaRepository<Sale, String> {	
    @Query("SELECT s FROM Sale s WHERE s.salesmanName = :salesmanName")
    List<Sale> findBySalesmanName(@Param("salesmanName") String salesmanName);
}
