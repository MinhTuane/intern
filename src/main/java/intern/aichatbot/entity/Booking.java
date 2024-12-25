package intern.aichatbot.entity;

import java.util.HashMap;
import java.util.Map;

import intern.aichatbot.mapper.StatusBooking;
import intern.aichatbot.mapper.StatusCash;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
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
@Table(name="Booking")
public class Booking {
    public Booking(String phoneNumber,String firstName,String lastName,
                    String address,StatusBooking status,StatusCash cash) {
        this.phoneNumber = phoneNumber;
        this.firstName= firstName;
        this.lastName = lastName;
        this.address = address;
        this.status = status;
        this.cash = cash;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String address;
    private StatusBooking status;
    private StatusCash cash;

    @ElementCollection
    @CollectionTable(name = "booking_products", joinColumns = @JoinColumn(name = "booking_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "order_quantity")
    private final Map<Product, Long> products = new HashMap<>();
}
