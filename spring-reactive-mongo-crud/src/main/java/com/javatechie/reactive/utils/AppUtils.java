package com.javatechie.reactive.utils;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.javatechie.reactive.dto.ProductDto;
import com.javatechie.reactive.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {
    public static ProductDto entitytoDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
