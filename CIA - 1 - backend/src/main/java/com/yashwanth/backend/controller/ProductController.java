package com.yashwanth.backend.controller;

import com.yashwanth.backend.exception.ProductNotFoundException;
import com.yashwanth.backend.model.Product;
import com.yashwanth.backend.repository.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
public class ProductController {
    @Autowired
    private Productrepo productRepo;

    @PostMapping("/product")
    public Product newProduct(@RequestBody Product newProduct) {
        return productRepo.save(newProduct);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
    @PutMapping("/product/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product existingProduct = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setImgurl(updatedProduct.getImgurl());
        existingProduct.setSeller(updatedProduct.getSeller());
        existingProduct.setOffer(updatedProduct.getOffer());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setClassification(updatedProduct.getClassification());

        return productRepo.save(existingProduct);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Product existingProduct = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepo.delete(existingProduct);
        return ResponseEntity.ok("Product deleted successfully");
    }

    // Handle ProductNotFoundException with a specific HTTP status
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
