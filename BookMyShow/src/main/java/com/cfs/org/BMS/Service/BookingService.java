package com.cfs.org.BMS.Service;

import com.cfs.org.BMS.DTO.*;
import com.cfs.org.BMS.Exception.ResourceNotFoundException;
import com.cfs.org.BMS.Exception.SeatUnavailableException;
import com.cfs.org.BMS.Model.*;
import com.cfs.org.BMS.Repository.BookingRepository;
import com.cfs.org.BMS.Repository.ShowRepository;
import com.cfs.org.BMS.Repository.ShowSeatRepository;
import com.cfs.org.BMS.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    //Create Booking
   @Transactional
    public BookingDTO CreateBooking(BookingRequestDTO bookingRequest){

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

        Show show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show Not Found"));

        List<ShowSeat> selectedSeats = showSeatRepository.findAllById(bookingRequest.getSeatIds());

        for(ShowSeat seat : selectedSeats){

            if(!"AVAILABLE".equals(seat.getStatus())){
                  throw new SeatUnavailableException("Seat "+seat.getSeat().getSeatNumber()+" is not available");
            }
             seat.setStatus("LOCKED");
        }
       showSeatRepository.saveAll(selectedSeats);

        Double totalAmount = selectedSeats.stream()
                .mapToDouble(ShowSeat::getPrice)
                .sum();

        //Payment
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentMethod(bookingRequest.getPaymentMethod());
        payment.setStatus("SUCCESS");
        payment.setTransactionId(UUID.randomUUID().toString());

        //Booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingTime(LocalDateTime.now());
        booking.setShowSeats(selectedSeats);
        booking.setStatus("CONFIRMED");
        booking.setTotalAmount(totalAmount);
        booking.setBookingNumber(UUID.randomUUID().toString());
        booking.setPayment(payment);

        Booking saveBooking = bookingRepository.save(booking);

        selectedSeats.forEach(seat ->
        {
            seat.setStatus("BOOKED");
            seat.setBooking(saveBooking);
        });

        showSeatRepository.saveAll(selectedSeats);

        return mapToBookingDto(saveBooking, selectedSeats);
    }
 //Get booking by ID;
    private BookingDTO getByBookingId(Long Id){
        Booking booking = bookingRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Booking Not Found"));

        List<ShowSeat> seats = showSeatRepository.findAll().stream()
                .filter(seat -> seat.getBooking() != null && seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());
        /*
        * The upper stream pipe line is a sorter version of
        * List<ShowSeat> seats = new ArrayList<>();
         * for(ShowSeat seat : showSeatRepository.findAll()){
        *
        * if(seat.getBooking() != null && seat.getBooking().getId().equals(booking.getId()){
        *   seats.add(seat);
        * }
        * }*/
        return mapToBookingDto(booking, seats);
    }

    //Get booking by BookingNumber;
    private BookingDTO getByBookingNumber(String bookingNumber){
       Booking booking = bookingRepository.findByBookingNumber(bookingNumber)
               .orElseThrow(()-> new ResourceNotFoundException("Booking Not Found"));

       List<ShowSeat> seats =  showSeatRepository.findAll().stream()
               .filter(seat -> seat.getBooking() != null && seat.getBooking().getId().equals(booking.getId()))
               .collect(Collectors.toList());

       return mapToBookingDto(booking, seats);
    }
    //Get by user;
    private List<BookingDTO> getBookingByUserID(Long userId){
       List<Booking> booking = bookingRepository.findByUserID(userId);
        return booking.stream()
                .map(booking1 -> {
                     List<ShowSeat> seats = showSeatRepository.findAll().stream()
                            .filter(seat -> seat.getBooking() != null && seat.getBooking().getId()
                                    .equals(booking1.getId()))
                             .collect(Collectors.toList());
                            return mapToBookingDto(booking1, seats);
                })
                .collect(Collectors.toList());
    }

    //Cancel Booking;
    @Transactional
    private BookingDTO cancelBooking(Long id){
       Booking booking = bookingRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));

        booking.setStatus("CANCELLED");
        List<ShowSeat> seats = showSeatRepository.findAll().stream()
                .filter(seat -> seat.getBooking() != null && seat.getBooking().getId()
                        .equals(booking.getId()))
                .collect(Collectors.toList());

        seats.forEach(seat ->{
            seat.setStatus("AVAILABLE");
            seat.setBooking(null);

        });

        if(booking.getPayment() != null){
            booking.getPayment().setStatus("REFUND");
        }
        Booking updateBooking = bookingRepository.save(booking);
        showSeatRepository.saveAll(seats);
        return mapToBookingDto(updateBooking,seats);
    }
    private BookingDTO mapToBookingDto(Booking booking, List<ShowSeat> seats){

        //Booking
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setBookingNumber(booking.getBookingNumber());
        bookingDTO.setBookingTime(booking.getBookingTime());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setTotalAmount(booking.getTotalAmount());

        //User
        UserDTO userDto = new UserDTO();
        userDto.setId(booking.getUser().getId());
        userDto.setName(booking.getUser().getName());
        userDto.setEmail(booking.getUser().getEmail());
        userDto.setPhoneNumber(booking.getUser().getPhoneNumber());
        bookingDTO.setUser(userDto);

       //Show
        ShowDTO showDTO = new ShowDTO();
        showDTO.setId(booking.getShow().getId());
        showDTO.setStartTime(booking.getShow().getStartTime());
        showDTO.setEndTime(booking.getShow().getEndTime());

        //Movie
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(booking.getShow().getMovie().getId());
        movieDTO.setTitle(booking.getShow().getMovie().getTitle());
        movieDTO.setDescription(booking.getShow().getMovie().getDescription());
        movieDTO.setLanguage(booking.getShow().getMovie().getLanguage());
        movieDTO.setGenre(booking.getShow().getMovie().getGenre());
        movieDTO.setDurationMins(booking.getShow().getMovie().getDurationMins());
        movieDTO.setPosterUrl(booking.getShow().getMovie().getPosterUrl());
        showDTO.setMovie(movieDTO);

        //Screen
        ScreenDTO screenDTO = new ScreenDTO();
        screenDTO.setId(booking.getShow().getScreen().getId());
        screenDTO.setName(booking.getShow().getScreen().getName());
        screenDTO.setTotalSeats(booking.getShow().getScreen().getTotalSeats());

        //Theater
        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setId(booking.getShow().getScreen().getTheater().getId());
        theaterDTO.setName(booking.getShow().getScreen().getTheater().getName());
        theaterDTO.setAddress(booking.getShow().getScreen().getTheater().getAddress());
        theaterDTO.setCity(booking.getShow().getScreen().getTheater().getCity());
        theaterDTO.setTotalScreens(booking.getShow().getScreen().getTheater().getTotalScreen());

        screenDTO.setTheater(theaterDTO);
        showDTO.setScreen(screenDTO);
        bookingDTO.setShow(showDTO);

        List<ShowSeatDTO> seatDTOS = seats.stream()
                .map(seat ->
                {
                    //Show seat DTO
                  ShowSeatDTO seatDto = new ShowSeatDTO();
                  seatDto.setId(seat.getId());
                  seatDto.setStatus(seat.getStatus());
                  seatDto.setPrice(seat.getPrice());

                  //Show Seat DTO
                    SeatDTO baseSeatDto = new SeatDTO();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setBasePrice(seat.getSeat().getBasePrice());
                    seatDto.setSeat(baseSeatDto);
                    return seatDto;
                })
                .collect(Collectors.toList());

           bookingDTO.setSeats(seatDTOS);

           if(booking.getPayment() != null){
               PaymentDTO paymentDTO = new PaymentDTO();
               paymentDTO.setId(booking.getPayment().getId());
               paymentDTO.setAmount(booking.getPayment().getAmount());
               paymentDTO.setPaymentMethod(booking.getPayment().getPaymentMethod());
               paymentDTO.setPaymentTime(booking.getPayment().getPaymentTime());
               paymentDTO.setTransactionId(booking.getPayment().getTransactionId());
               bookingDTO.setPayment(paymentDTO);
           }

           return bookingDTO;
    }
}
