package intern.aichatbot.service;

public interface ProductService {
    ProductDetails getMostPopularProduct();
    ProductDetails getDetails(String nameProduct);
}
