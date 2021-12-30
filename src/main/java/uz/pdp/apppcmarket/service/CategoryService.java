package uz.pdp.apppcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.pdp.apppcmarket.dto.CategoryDto;
import uz.pdp.apppcmarket.dto.Response;
import uz.pdp.apppcmarket.entity.Category;
import uz.pdp.apppcmarket.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Page<Category> getAllCategoryService(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage;
    }

    public Category getById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }


    public Category save(CategoryDto categoryDto) {
        Category category = new Category();
        if (categoryDto.getParentCategoryId() == null) {
            category.setParentCategory(null);
            category.setName(categoryDto.getName());
        } else {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalCategory.isPresent()) {
                return null;
            }
            category.setParentCategory(optionalCategory.get());
            category.setName(categoryDto.getName());
        }
        return categoryRepository.save(category);
    }
}
