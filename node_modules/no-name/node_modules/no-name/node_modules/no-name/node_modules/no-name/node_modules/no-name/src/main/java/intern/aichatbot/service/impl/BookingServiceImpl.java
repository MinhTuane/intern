package intern.aichatbot.service.impl;

import org.springframework.stereotype.Service;

import intern.aichatbot.entity.Booking;
import intern.aichatbot.entity.Product;
import intern.aichatbot.mapper.BookingMapper;
import intern.aichatbot.mapper.StatusBooking;
import intern.aichatbot.mapper.StatusCash;
import intern.aichatbot.repositoriy.BookingRepository;
import intern.aichatbot.repositoriy.ProductRepository;
import intern.aichatbot.service.BookingDetails;
import intern.aichatbot.service.BookingService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ProductRepository productRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, ProductRepository productRepository) {
        super();
        this.bookingRepository = bookingRepository;
        this.productRepository = productRepository;
    }

    private Booking caches;

    @Override
    public BookingDetails getBooking(long bookingNumber, String phoneNumber, String firstName) {
        Booking booking = bookingRepository.findByIdWithProducts(bookingNumber)
                .filter(b -> b.getPhoneNumber().equals(phoneNumber))
                .filter(b -> b.getFirstName().equalsIgnoreCase(firstName))
                .orElseThrow(() -> new EntityNotFoundException("Booking Id is not correct!"));
        this.caches = booking;
        return BookingMapper.mapToBookingDetails(booking);
    }

    @Override
    public Boolean changeBookingStatus(StatusBooking status) {
        if (caches != null) {
            caches.setStatus(status);
            bookingRepository.save(caches);
            return true;
        }
        return false;
    }

    @Override
    public Boolean changeInformation(Long id,String phoneNumber, String address, String firstName, String lastName) {
        Booking booking = bookingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Id booking is not correct"));
        if(phoneNumber !=null) {
            booking.setPhoneNumber(phoneNumber);
        }
        if(address != null) {
            booking.setAddress(address);
        }
        if(firstName!=null) {
            booking.setFirstName(firstName);
        }
        if(lastName!=null) {
            booking.setLastName(lastName);
        }
        this.bookingRepository.save(booking);
        return true;
    }

    @Override
    public Boolean eraseProduct(String nameProduct) {
        if (caches == null) {
            throw new IllegalStateException("No booking was selected");
        }
        Product product = caches.getProducts()
                .keySet().stream().filter(p -> p.getName()
                        .equals(nameProduct))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product with name " + nameProduct + " not found in the booking."));

        caches.getProducts().remove(product);
        bookingRepository.save(caches);
        return true;
    }

    @Override
    public Boolean modifyProductQuantity(String nameProduct, Long quantity) {
        if (caches == null) {
            throw new IllegalStateException("No booking was selected");
        }
        Product product = caches.getProducts()
                .keySet().stream().filter(p -> p.getName()
                        .equals(nameProduct))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product with name " + nameProduct + " not found in the booking."));

        caches.getProducts().put(product, quantity);
        bookingRepository.save(caches);
        return true;
    }

    @Override
    public Boolean createABooking(String phoneNumber, String firstName, String lastName, String address) {
        Booking booking = new Booking(phoneNumber, firstName, lastName, address, StatusBooking.Confirm, StatusCash.Cod);
        this.caches = bookingRepository.save(booking);
        return true;
    }

    @Override
    public Boolean addProductToBooking( String nameProduct, Long quantity) {
        Product product = getProduct(nameProduct);
        caches.getProducts().merge(product, quantity, Long::sum); 
        bookingRepository.save(caches); 
        return true;
    }

    public Product getProduct(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(
                        "We do not have " + name + " product or you may provide a wrong name."));
    }

}
