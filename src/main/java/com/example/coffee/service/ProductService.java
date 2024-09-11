package com.example.coffee.service;


import com.example.coffee.dto.ProductDTO;
import com.example.coffee.entity.Product;
import com.example.coffee.exception.ProductException;
import com.example.coffee.repositroy.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;


    //R
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.forEach(product -> productDTOS.add(new ProductDTO(product)));


        return productDTOS;
    }

    //C
    public ProductDTO register(ProductDTO productDTO) {

        Product product = productDTO.toEntity();

        productRepository.save(product);

        return new ProductDTO(product);
    }
    //U
    public ProductDTO modify(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getProductId()).orElse(null);

        try {
            product.changePname(productDTO.getProduct_name());
            product.changePrice(productDTO.getPrice());
            product.changeDescription(productDTO.getDescription());
            product.changeCategory(productDTO.getCategory());
            log.info("product------- : "+product);
            return new ProductDTO(product);
        }catch (Exception e){
            log.error("--- 에러메세지 : " + e.getMessage());
            throw ProductException.NOT_MODIFIED.get();
        }

    }

    //D
    public void remove(Long pid) {
        Optional<Product> foundProduct = productRepository.findById(pid);
        Product product = foundProduct.orElseThrow(ProductException.NOT_MODIFIED::get);
        productRepository.delete(product);

    }
}
