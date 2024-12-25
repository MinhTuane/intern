package intern.aichatbot.repositoriy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import intern.aichatbot.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.sold = (SELECT MAX(p2.sold) FROM Product p2)")
    Optional<Product> getMostPopularProduct();

    @Query("SELECT p FROM Product p WHERE p.name = :nameProduct")
    Optional<Product> findByName(String nameProduct);
}
