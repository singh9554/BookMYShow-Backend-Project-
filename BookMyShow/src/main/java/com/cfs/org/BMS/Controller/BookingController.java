package com.cfs.org.BMS.Controller;

import com.cfs.org.BMS.DTO.BookingDTO;
import com.cfs.org.BMS.DTO.BookingRequestDTO;
import com.cfs.org.BMS.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequest){
        return new ResponseEntity<>(bookingService.CreateBooking(bookingRequest),HttpStatus.CREATED);
    }
}
