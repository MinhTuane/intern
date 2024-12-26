package intern.aichatbot.service.impl;

import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.Tool;
import intern.aichatbot.mapper.StatusBooking;
import intern.aichatbot.service.BookingDetails;
import intern.aichatbot.service.BookingService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BookingTools {
    BookingService bookService;
    

    @Tool("""
            Retrieves information about an existing booking. For products show name of product and quantity corresponding booked product.
            """)
    public BookingDetails getBookingDetails(long booking,String phoneNumber,String firstName) {
        return bookService.getBooking(booking, phoneNumber, firstName);
    }

    @Tool("""
        Customer can change cancel a booking or change status from cancel to confirm.
        At first ask for booking detail if there are none to get booking then have information to execute this function. If the booking have been delivered then this booking can not change anymore!"""
        )
    public Boolean changeBookingStatus(StatusBooking status) {
        return bookService.changeBookingStatus(status);
    }

    @Tool("""
        Customer can change address . 
         Every change need to be confirmation."""
        )
    public Boolean changeAddress(Long bookingId,String address) {
        return bookService.changeInformation(bookingId,  null, address,null,null);
    }
    @Tool("""
        Customer can change address ,phone number, first name,last name. No need ask for confirm phone number anymore.
         Every change need to be confirmation(yes no confirm)."""
        )
    public Boolean changeInformation(Long bookingId,String phone,String address,String firstName,String lastName) {
        return bookService.changeInformation(bookingId,phone, address, firstName, lastName);
    }

    @Tool("""
        Customer can change Phone number . 
         No need ask for confirm phone number anymore.
         Every change need to be confirmation(yes no confirm)."""
        )
    public Boolean changePhoneNumber(Long bookingId,String phone) {
        return bookService.changeInformation( bookingId, phone, null,null,null);
    }

    @Tool("""
        Customer can change their name . 
         No need ask for confirm phone number anymore.
         Every change need to be confirmation(yes no confirm)."""
        )
    public Boolean changeName(Long bookingId,String firstName,String lastName) {
        return bookService.changeInformation( bookingId, null, null,firstName,lastName);
    }

    @Tool("""
        Customer erase product which they had booked . 
        At first ask for booking detail if there are none to get booking then have information to execute this function. Every change need to be confirmation."""
        )
    public Boolean eraseProduct(String productName) {
        return bookService.eraseProduct( productName );
    }

    @Tool("""
        Customer change quantity of product which they had booked . 
        At first ask for booking detail if there are none to get booking then have information to execute this function. Every change need to be confirmation."""
        )
    public Boolean changeProductQuantity(String productName,Long quantity) {
        return bookService.modifyProductQuantity( productName,quantity );
    }

    @Tool("""
        Help customer create a booking then using add product tool to ask for customer product they want to purchase."""
        )
    public Boolean createBooking(String phoneNumber, String firstName, String lastName, String address) {
        return bookService.createABooking( phoneNumber,firstName,lastName,address);
    }

    @Tool("""
        this provide method to add product to new booking.Continously execute this method till the user finish."""
        )
    public Boolean addProduct(String productName,Long quantity) {
        return bookService.addProductToBooking( productName,quantity );
    }
}
