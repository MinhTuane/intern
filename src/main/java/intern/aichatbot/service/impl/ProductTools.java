package intern.aichatbot.service.impl;

import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.Tool;
import intern.aichatbot.service.ProductDetails;
import intern.aichatbot.service.ProductService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductTools {
    ProductService productService;

    @Tool("""
            Provide for customer most poplar product such as name number of sold and rating of it.
            """)
    public ProductDetails getMostPopularProduct() {
        return productService.getMostPopularProduct();
    }

    @Tool("""
                Provide customer with the availability of the product.
                If available quantity is 0, inform the customer that the product is not available at the moment and provide the total stock available.
            """)
    public ProductDetails getAailable(String nameProduct) {
        return productService.getDetails(nameProduct);
    }
}
