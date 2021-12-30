package uz.pdp.apppcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import uz.pdp.apppcmarket.entity.Brand;
import uz.pdp.apppcmarket.entity.Product;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "brand")
public interface BrandRepository extends JpaRepository<Brand,Integer> {
    @RestResource(path = "byName")
    Optional<Brand> findByName(String name);





}
