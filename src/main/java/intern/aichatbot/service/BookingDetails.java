package intern.aichatbot.service;

import java.util.Map;

public record BookingDetails(Long id,String phoneNumber,String firstName,String lastName,String address,Map<Long,Long> products) {

}
