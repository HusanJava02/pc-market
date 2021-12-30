package uz.pdp.apppcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apppcmarket.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
