package intern.aichatbot.service;

import java.util.Map;

import intern.aichatbot.mapper.StatusBooking;
import intern.aichatbot.mapper.StatusCash;

public record BookingDetails(Long id,String phoneNumber,String firstName,String lastName,String address,Map<String,Long> products, StatusBooking status,
     StatusCash cash) {

}
