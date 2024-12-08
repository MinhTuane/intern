package intern.aichatbot.mapper;

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
            booking.getProducts()
        );
    }
}
