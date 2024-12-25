package intern.aichatbot.repositoriy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import intern.aichatbot.entity.Booking;


public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.products WHERE b.id = :booking_id")
    Optional<Booking> findByIdWithProducts(@Param("booking_id") long bookingNumber);
}
