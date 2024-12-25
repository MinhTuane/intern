package intern.aichatbot.service;

import intern.aichatbot.mapper.StatusBooking;

public interface BookingService {
    BookingDetails getBooking(long bookingId, String phoneNumber, String firstName);

    Boolean changeBookingStatus(StatusBooking status);

    Boolean changeInformation(String phoneNumber, String adress, String firstName, String lastName);

    Boolean eraseProduct(String nameProduct);

    Boolean modifyProductQuantity(String nameProduct, Long quantity);

    Boolean createABooking(String phoneNumber, String firstName, String lastName, String address);

    Boolean addProductToBooking(String nameProduct, Long quantity);
}
