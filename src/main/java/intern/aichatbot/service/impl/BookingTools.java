package intern.aichatbot.service.impl;

import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.Tool;
import intern.aichatbot.service.BookingDetails;
import intern.aichatbot.service.BookingService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BookingTools {
    BookingService bookService;

    @Tool("""
            Retrieves information about an existing booking.
            """)
    public BookingDetails getBookingDetails(long booking,String phoneNumber,String firstName) {
        return bookService.getBooking(booking, phoneNumber, firstName);
    }
}
