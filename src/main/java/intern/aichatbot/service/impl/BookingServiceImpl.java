package intern.aichatbot.service.impl;

import org.springframework.stereotype.Service;

import intern.aichatbot.entity.Booking;
import intern.aichatbot.mapper.BookingMapper;
import intern.aichatbot.repositoriy.BookingRepository;
import intern.aichatbot.service.BookingDetails;
import intern.aichatbot.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    
    @Override
    public BookingDetails getBooking(long bookingNumber, String phoneNumber, String firstName) {
        Booking booking = bookingRepository.findByIdWithProducts(bookingNumber)
                        .filter(b -> b.getPhoneNumber().equals(phoneNumber))
                        .filter(b -> b.getFirstName().equalsIgnoreCase(firstName))
                        .orElseThrow(() -> new EntityNotFoundException("Booking Id is not correct!"));
    
    return BookingMapper.mapToBookingDetails(booking);
}
   

}
