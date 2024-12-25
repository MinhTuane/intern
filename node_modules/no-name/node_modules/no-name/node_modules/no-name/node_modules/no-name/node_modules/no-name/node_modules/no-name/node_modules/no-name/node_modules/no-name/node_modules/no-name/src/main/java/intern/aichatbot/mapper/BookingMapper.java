package intern.aichatbot.mapper;

import java.util.Map;
import java.util.stream.Collectors;

import intern.aichatbot.entity.Booking;
import intern.aichatbot.service.BookingDetails;

public class BookingMapper {

    public static BookingDetails mapToBookingDetails(Booking booking) {
        return new BookingDetails(
            booking.getId(),
            booking.getPhoneNumber(),
            booking.getFirstName(),
            booking.getLastName(),
            booking.getAddress(),
            booking.getProducts().entrySet().stream()
            .collect(Collectors.toMap(entry -> entry.getKey().getName(), Map.Entry::getValue)),
            booking.getStatus(),
            booking.getCash()
        );
    }
}
