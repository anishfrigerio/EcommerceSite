package it.attf.ecommerceSite.service;

import it.attf.ecommerceSite.models.Category;
import it.attf.ecommerceSite.repository.CategoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepo categoryrepo;

    public CategoryService(CategoryRepo categoryrepo) {
        this.categoryrepo = categoryrepo;
    }

    public List<Category> listCategories() {
        return categoryrepo.findAll();
    }

    public void createCategory(Category category) {
        categoryrepo.save(category);
    }

    public Category readCategory(String categoryName) {
        return categoryrepo.findByCategoryName(categoryName);
    }

    public Optional<Category> readCategory(Integer categoryId) {
        return categoryrepo.findById(categoryId);
    }

    public void updateCategory(Integer categoryID, Category newCategory) {
        Category category = categoryrepo.findById(categoryID).get();
        category.setCategoryName(newCategory.getCategoryName());
        category.setDescription(newCategory.getDescription());
        category.setProducts(newCategory.getProducts());
        category.setImageUrl(newCategory.getImageUrl());

        categoryrepo.save(category);
    }
}
