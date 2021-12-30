package uz.pdp.apppcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.pdp.apppcmarket.entity.Product;
import uz.pdp.apppcmarket.entity.Supplier;
import uz.pdp.apppcmarket.projection.SupplierWithIdProjection;

@RepositoryRestResource(path = "supplier",excerptProjection = SupplierWithIdProjection.class)
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

}
