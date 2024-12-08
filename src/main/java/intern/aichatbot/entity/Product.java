package intern.aichatbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Product")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private Long stock;
    private Long available;

    public String getFromStock(Long numberOfProduct) {
        if(numberOfProduct > stock) {
            throw new IllegalArgumentException( "The number of products is out of stock!!!");
        }

        this.setStock(stock-numberOfProduct);
        this.setAvailable(available+numberOfProduct);
        return "Transfer Successful";
    }
}
