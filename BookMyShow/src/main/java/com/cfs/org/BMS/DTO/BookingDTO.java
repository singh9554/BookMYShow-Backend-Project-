package com.cfs.org.BMS.DTO;


import com.cfs.org.BMS.Model.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Long id;
    private String bookingNumber;
    private LocalDateTime bookingTime;
    private UserDTO user;
    private ShowDTO show;
    private String status;
    private double totalAmount;
    private List<ShowSeatDTO> seats;
    private PaymentDTO payment;
}
