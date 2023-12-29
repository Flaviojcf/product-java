package com.example.demo.domain.services;

import com.example.demo.domain.DTO.RequestProductDTO;
import com.example.demo.domain.product.Product;
import com.example.demo.domain.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    @Autowired
    private ProductRepository _productRepository;

    public List<Product> GetAllProducts(){
        return  _productRepository.findAllByActiveTrue();
    };

    public void CreateProduct(Product product){
        _productRepository.save(product);
    }

    public Product UpdateProduct(RequestProductDTO requestProductDTO){
        Optional<Product> optionalProduct = _productRepository.findById(requestProductDTO.id());

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(requestProductDTO.name());
            product.setPrice_in_cents(requestProductDTO.price_in_cents());
            return product;
        } else {
            throw  new EntityNotFoundException();
        }
    };

    public Product DeleteProduct(String id){
        Optional<Product> optionalProduct = _productRepository.findById(id);

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setActive(false);
            return product;
        } else {
            throw  new EntityNotFoundException();
        }
    };
}
