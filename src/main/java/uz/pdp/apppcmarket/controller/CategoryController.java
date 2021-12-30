package uz.pdp.apppcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apppcmarket.dto.CategoryDto;
import uz.pdp.apppcmarket.dto.Response;
import uz.pdp.apppcmarket.entity.Category;
import uz.pdp.apppcmarket.entity.Product;
import uz.pdp.apppcmarket.service.CategoryService;

@RestController
@RequestMapping(value = "api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "20") Integer size){
        Page<Category> categoryPage =  categoryService.getAllCategoryService(size,page);
        return ResponseEntity.ok(categoryPage);
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Category category = categoryService.getById(id);
        return ResponseEntity.status(category != null ? HttpStatus.OK:HttpStatus.NOT_FOUND).body(category);
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody CategoryDto categoryDto){
        Category savedCategory = categoryService.save(categoryDto);
        return ResponseEntity.status(savedCategory != null ? HttpStatus.CREATED:HttpStatus.CONFLICT).body(savedCategory);
    }




}
