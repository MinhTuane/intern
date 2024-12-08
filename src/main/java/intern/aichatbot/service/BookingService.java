package intern.aichatbot.service;

public interface BookingService {
    BookingDetails getBooking(long bookingId, String phoneNumber,String firstName);
}
