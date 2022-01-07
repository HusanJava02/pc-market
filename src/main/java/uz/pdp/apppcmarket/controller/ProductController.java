package uz.pdp.apppcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apppcmarket.dto.ObjectResponse;
import uz.pdp.apppcmarket.dto.ProductDto;
import uz.pdp.apppcmarket.dto.Response;
import uz.pdp.apppcmarket.entity.Product;
import uz.pdp.apppcmarket.service.ProductService;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "20") Integer size, @RequestParam(defaultValue = "0") Integer page){
        Page<Product> productPage =  productService.getAllService(size,page);
        return ResponseEntity.ok(productPage);
    }

    @PostMapping
    @PreAuthorize(value = "hasAnyRole('MODERATOR','SUPER_ADMIN')")
    public HttpEntity<?> add(@RequestBody ProductDto productDto){
        Response response = productService.addProduct(productDto);
        return ResponseEntity.status(response.getSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    public HttpEntity<?> delete(@PathVariable Integer id){
        Response response = productService.deleteById(id);
        return ResponseEntity.status(response.getSuccess()?HttpStatus.NO_CONTENT:HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody ProductDto productDto){
        ObjectResponse objectResponse = productService.editProduct(id, productDto);
        return ResponseEntity.status(objectResponse.getResponse().getSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(objectResponse);

    }


}
