package com.apple.iphonestorespringboot.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.model.Product;
import com.apple.iphonestorespringboot.model.Size;
import com.apple.iphonestorespringboot.request.CreateProductRequest;

public interface ProductService {
    
    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId,Product product) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product>findProductByCategory(String category);

    public Page<Product>getAllProduct(String category,List<String>color,List<String>sizes,Integer minPrice,Integer maxPrice
    ,Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);
    
}
