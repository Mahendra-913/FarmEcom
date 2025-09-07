
package com.ECommerce.FarmEcom.controller;

import com.ECommerce.FarmEcom.model.Product;
import com.ECommerce.FarmEcom.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    // Constructor injection
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //  GET - saare products list karo
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //  GET - ek product id se fetch karo
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    //  POST - naya product add karo
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    //  PUT - product update karo
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setPrice(productDetails.getPrice());
                    product.setQuantity(productDetails.getQuantity());
                    return productRepository.save(product);
                })
                .orElse(null);
    }

    // DELETE - product delete karo
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Product deleted with id: " + id;
    }
}
