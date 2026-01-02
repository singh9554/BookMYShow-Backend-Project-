package com.cfs.org.BMS.Controller;

import com.cfs.org.BMS.DTO.BookingDTO;
import com.cfs.org.BMS.DTO.BookingRequestDTO;
import com.cfs.org.BMS.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequest){
        return new ResponseEntity<>(bookingService.CreateBooking(bookingRequest),HttpStatus.CREATED);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long Id){
        return ResponseEntity.ok(bookingService.getByBookingId(Id));
    }
}
