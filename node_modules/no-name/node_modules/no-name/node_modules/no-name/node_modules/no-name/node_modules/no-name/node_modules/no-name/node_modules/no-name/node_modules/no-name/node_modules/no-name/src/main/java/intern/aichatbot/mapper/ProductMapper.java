package intern.aichatbot.mapper;

import intern.aichatbot.entity.Product;
import intern.aichatbot.service.ProductDetails;

public class ProductMapper {
    public static ProductDetails mapProductDetails(Product product) {
        return new ProductDetails(product.getId(),
         product.getName(),
          product.getPrice(),
           product.getStock(), 
           product.getAvailable(), 
           product.getSold(), 
           product.getRate());
    }
}
