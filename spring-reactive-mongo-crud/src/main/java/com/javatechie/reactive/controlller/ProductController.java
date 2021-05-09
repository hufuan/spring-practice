package com.javatechie.reactive.controlller;

import com.javatechie.reactive.dto.ProductDto;
import com.javatechie.reactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public Flux<ProductDto> products() {
        return service.getProducts();
    }
    @GetMapping("/{id}")
    public Mono<ProductDto> getProduct(@PathVariable String id) {
        return service.getProduct(id);
    }

    @GetMapping("/product-range")
    public Flux<ProductDto> getProductBetweenRange(@RequestParam("min") double min,
                                                   @RequestParam("max") double max) {
        return service.getProductInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> savaProduct(@RequestBody Mono<ProductDto> productMono) {
        System.out.println("post :" + productMono);
        return service.saveProduct(productMono);
    }

    @PutMapping("/update/{id}")
    public Mono<ProductDto> savaProduct(@RequestBody Mono<ProductDto> productMono, @PathVariable String id) {
        return service.updateProduct(productMono, id);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return service.deleteProduct(id);
    }
}
