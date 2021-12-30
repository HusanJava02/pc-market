package uz.pdp.apppcmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.apppcmarket.dto.ObjectResponse;
import uz.pdp.apppcmarket.dto.ProductDto;
import uz.pdp.apppcmarket.dto.Response;
import uz.pdp.apppcmarket.entity.*;
import uz.pdp.apppcmarket.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final DetailRepository detailRepository;
    private final ProductRepository productRepository;
    private final AttachmentRepository attachmentRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public Page<Product> getAllService(Integer size, Integer page) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Response addProduct(ProductDto productDto) {
        Integer attachmentId = productDto.getAttachmentId();
        Integer brandId = productDto.getBrandId();
        Integer categoryId = productDto.getCategoryId();
        Integer supplierId = productDto.getSupplierId();
        Product product = new Product();
        product.setActive(productDto.isActive());
        product.setDescription(productDto.getDescription());
        product.setModel(productDto.getModel());
        List<Detail> details = productDto.getDetails();
        List<Detail> savedAllDetails = detailRepository.saveAll(details);
        product.setDetails(savedAllDetails);
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (!optionalAttachment.isPresent())
            return new Response(false, "not found attachment with given id");
        Attachment attachment = optionalAttachment.get();
        product.setAttachment(attachment);
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);
        if (!optionalBrand.isPresent())
            return new Response(false,"not found Brand");
        Brand brand = optionalBrand.get();
        product.setBrand(brand);
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent())
            return new Response(false,"not found category");
        Category category = optionalCategory.get();
        product.setCategory(category);
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if (!optionalSupplier.isPresent())
            return new Response(false,"not found supplier");
        product.setSupplier(optionalSupplier.get());
        productRepository.save(product);
        return new Response(true,"successfully saved");
    }

    public Response deleteById(Integer id) {
        if (productRepository.existsById(id)){
            productRepository.deleteById(id);
            return new Response(true,"product deleted");
        }else return new Response(false,"not found");
    }

    public ObjectResponse editProduct(Integer id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return new ObjectResponse(null,new Response(false,"not found product"));
        Product product = optionalProduct.get();
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setModel(productDto.getModel());
        product.setActive(productDto.isActive());
        product.setDescription(productDto.getDescription());
        Integer attachmentId = productDto.getAttachmentId();
        Integer brandId = productDto.getBrandId();
        Integer supplierId = productDto.getSupplierId();
        List<Detail> details = productDto.getDetails();
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (!optionalAttachment.isPresent())
            return new ObjectResponse(null,new Response(false,"not found attachment with given id"));
        Attachment attachment = optionalAttachment.get();
        product.setAttachment(attachment);
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);
        if (!optionalBrand.isPresent())
            return new ObjectResponse(null,new Response(false,"not found Brand with given id"));
        Brand brand = optionalBrand.get();
        product.setBrand(brand);
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if (!optionalSupplier.isPresent())
            return new ObjectResponse(null,new Response(false,"not found supplier with given id"));
        Supplier supplier = optionalSupplier.get();
        product.setSupplier(supplier);
        List<Detail> detailFromDb = product.getDetails();
        for (int i = 0; i < detailFromDb.size(); i++) {
            Detail fromDb = detailFromDb.get(i);
            Detail fromDto = details.get(i);
            fromDb.setName(fromDto.getName());
            fromDb.setDescription(fromDto.getDescription());
            fromDb.setValue(fromDto.getValue());
        }
        if (details.size() - detailFromDb.size() > 0){
            List<Detail> subList = details.subList(detailFromDb.size(), details.size());
            List<Detail> savedExtraDetails = detailRepository.saveAll(subList);
            detailFromDb.addAll(savedExtraDetails);
        }
        product.setDetails(detailFromDb);
        Product save = productRepository.save(product);
        return new ObjectResponse(save,new Response(true,"succesfully edited"));

    }

}
