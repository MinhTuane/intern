package intern.aichatbot.service.impl;

import org.springframework.stereotype.Service;

import intern.aichatbot.entity.Product;
import intern.aichatbot.mapper.ProductMapper;
import intern.aichatbot.repositoriy.ProductRepository;
import intern.aichatbot.service.ProductDetails;
import intern.aichatbot.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Override
    public ProductDetails getMostPopularProduct() {
        Product product = productRepository.getMostPopularProduct()
                .orElseThrow(() -> new EntityNotFoundException("No product to be found"));
        return ProductMapper.mapProductDetails(product);
    }

    @Override
    public ProductDetails getDetails(String nameProduct) {
        Product product = productRepository.findByName(nameProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with name: " + nameProduct));

        return ProductMapper.mapProductDetails(product);
    }
}
