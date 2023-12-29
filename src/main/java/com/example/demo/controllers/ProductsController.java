package com.example.demo.controllers;

import com.example.demo.domain.DTO.RequestProductDTO;
import com.example.demo.domain.product.Product;
import com.example.demo.domain.product.ProductRepository;
import com.example.demo.domain.services.ProductsService;
import jakarta.persistence.EntityNotFoundException;
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
    private ProductsService _productService;

    @GetMapping
    public ResponseEntity GetAllProducts(){

        var productL = _productService.GetAllProducts();

        return ResponseEntity.ok(productL);

    };

    @PostMapping
    public ResponseEntity CreateProduct(@RequestBody @Valid RequestProductDTO requestProductDTO){
        Product newProduct =  new Product(requestProductDTO);
        _productService.CreateProduct(newProduct);
        return ResponseEntity.ok(newProduct);
    };

    @PutMapping
    @Transactional
    public ResponseEntity UpdateProduct(@RequestBody @Valid RequestProductDTO requestProductDTO){
        Product product = _productService.UpdateProduct(requestProductDTO);

        if (product != null){
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }

    };


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity DeleteProduct(@PathVariable String id) {

        Product product = _productService.DeleteProduct(id);

        if (product != null){
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }

    };
}
