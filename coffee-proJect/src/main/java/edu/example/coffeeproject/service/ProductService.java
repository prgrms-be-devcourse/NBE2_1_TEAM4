package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO add(ProductDTO productDTO){
        Product save = productRepository.save(productDTO.toEntity());

        return new ProductDTO(save);
    }

    public ProductDTO read(Long productId){
        Optional<Product> product =
                productRepository.findById(productId);

        return new ProductDTO(product.get());
    }

    public ProductDTO update(ProductDTO productDTO){
        Optional<Product> product = productRepository.findById(productDTO.getProductId());
        Product product1 = product.orElseThrow(() -> new RuntimeException("Product not found"));

        try {
            product1.changeProductName(productDTO.getProductName());
            product1.changeCategory(productDTO.getCategory());
            product1.changePrice(productDTO.getPrice());
            product1.changeDescription(productDTO.getDescription());

            return new ProductDTO(productRepository.save(product1));
        }catch (Exception e){
            log.error(e);
            throw new RuntimeException("Product not found");
        }

    }

    public void remove(Long productId){
        productRepository.deleteById(productId);
    }

    public List<Product> readAll(){
        return productRepository.findAll();
    }
}
