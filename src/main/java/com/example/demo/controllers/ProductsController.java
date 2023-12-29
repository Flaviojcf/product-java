package com.example.demo.controllers;

import com.example.demo.domain.DTO.RequestProductDTO;
import com.example.demo.domain.product.Product;
import com.example.demo.domain.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductsController {

    @Autowired
    private ProductRepository _productRepository;

    @GetMapping
    public ResponseEntity GetAllProducts(){

        var productL = _productRepository.findAll();

        return ResponseEntity.ok(productL);

    };

    @PostMapping
    public ResponseEntity CreateProduct(@RequestBody @Valid RequestProductDTO requestProductDTO){
        Product newProduct =  new Product(requestProductDTO);
        _productRepository.save(newProduct);
        return ResponseEntity.ok(newProduct);
    };

    @PutMapping
    @Transactional
    public ResponseEntity UpdateProduct(@RequestBody @Valid RequestProductDTO requestProductDTO){
        Optional<Product> optionalProduct = _productRepository.findById(requestProductDTO.id());

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(requestProductDTO.name());
            product.setPrice_in_cents(requestProductDTO.price_in_cents());
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();

        }
    };
}
