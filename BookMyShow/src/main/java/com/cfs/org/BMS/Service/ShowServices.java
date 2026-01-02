package com.cfs.org.BMS.Service;

import com.cfs.org.BMS.DTO.*;
import com.cfs.org.BMS.Exception.ResourceNotFoundException;
import com.cfs.org.BMS.Model.*;
import com.cfs.org.BMS.Repository.MovieRepository;
import com.cfs.org.BMS.Repository.ScreenRepository;
import com.cfs.org.BMS.Repository.ShowRepository;
import com.cfs.org.BMS.Repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ShowServices {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public ShowDTO createShow(ShowDTO showDTO){

        Show show = new Show();

        Movie movie = movieRepository.findById(showDTO.getMovie().getId())
                .orElseThrow(()-> new ResourceNotFoundException("Movie not found"));

        Screen screen = screenRepository.findById(showDTO.getScreen().getId())
                .orElseThrow(()-> new ResourceNotFoundException("Screen Not found"));

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(showDTO.getStartTime());
        show.setEndTime(showDTO.getEndTime());

        Show saveShow = showRepository.save(show);

        List<ShowSeat> availableSeat = showSeatRepository.
                findByShowIDAndStatus(saveShow.getID(), "AVAILABLE");

        return mapToDto(saveShow,availableSeat);
    }

    //Find Show by Id
    public ShowDTO getShowById(Long Id){
        Show show = showRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Show not found by Id: "+Id));

        List<ShowSeat> availableSeat = showSeatRepository.
                findByShowIDAndStatus(show.getID(), "AVAILABLE");

        return mapToDto(show,availableSeat);

    }

    //Find all Show
    public List<ShowDTO> getAllShow(){
        List<Show> show = showRepository.findAll();

        return show.stream()
                .map(shows ->{
                    List<ShowSeat> availableSeat = showSeatRepository.
                            findByShowIDAndStatus(shows.getID(),"AVAILABLE");

                    return mapToDto(shows, availableSeat);
                }).collect(Collectors.toList());
    }
    //Get show by movie
    private List<ShowDTO> getShowByMovie(Long movieId){
        List<Show> show = showRepository.findByMovieID(movieId);

        return show.stream()
                .map(shows ->{
                    List<ShowSeat> availableSeat = showSeatRepository.
                            findByShowIDAndStatus(shows.getID(),"AVAILABLE");

                    return mapToDto(shows, availableSeat);
                }).collect(Collectors.toList());
    }
    //Get by movie and city
    private List<ShowDTO> getShowByMovieAndCity(Long movieId, String city){
        List<Show> show = showRepository.findByMovie_IDAndScreen_Theater_city(movieId, city);

        return show.stream()
                .map(shows ->{
                    List<ShowSeat> availableSeat = showSeatRepository.
                            findByShowIDAndStatus(shows.getID(),"AVAILABLE");

                    return mapToDto(shows, availableSeat);
                }).collect(Collectors.toList());
    }
    //find by stat and end time
    private List<ShowDTO> getShowByStartAndEndTime(LocalDateTime start, LocalDateTime end){
        List<Show> show = showRepository.findByStartTimeBetween(start, end);

        return show.stream()
                .map(shows ->{
                    List<ShowSeat> availableSeat = showSeatRepository.
                            findByShowIDAndStatus(shows.getID(),"AVAILABLE");

                    return mapToDto(shows, availableSeat);
                }).collect(Collectors.toList());
    }
    //map to showDTO
    private ShowDTO mapToDto(Show show, List<ShowSeat> availableSeat){

        ShowDTO showDTO = new ShowDTO();

        showDTO.setId(show.getID());
        showDTO.setStartTime(show.getStartTime());
        showDTO.setEndTime(show.getEndTime());
        showDTO.setMovie(new MovieDTO(
                show.getMovie().getID(),
                show.getMovie().getTitle(),
                show.getMovie().getDescription(),
                show.getMovie().getLanguage(),
                show.getMovie().getGenre(),
                show.getMovie().getDurationMins(),
                show.getMovie().getReleaseDate(),
                show.getMovie().getPosterUrl()

        ));

        TheaterDTO theaterDTO = new TheaterDTO(
                show.getScreen().getTheater().getID(),
                show.getScreen().getTheater().getName(),
                show.getScreen().getTheater().getAddress(),
                show.getScreen().getTheater().getCity(),
                show.getScreen().getTheater().getTotalScreen()
        );

        ScreenDTO screenDTO = new ScreenDTO(
                show.getScreen().getID(),
                show.getScreen().getName(),
                show.getScreen().getTotalSeats(),
                theaterDTO
        );
        showDTO.setScreen(screenDTO);

        List<ShowSeatDTO> showSeats = availableSeat.stream()
                .map(seat ->{
                    ShowSeatDTO showSeatDTO = new ShowSeatDTO();
                    showSeatDTO.setId(seat.getId());
                    showSeatDTO.setStatus(seat.getStatus());
                    showSeatDTO.setPrice(seat.getPrice());

                    SeatDTO baseSeatDTO = new SeatDTO();
                    baseSeatDTO.setId(seat.getSeat().getId());
                    baseSeatDTO.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatDTO.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDTO.setBasePrice(seat.getSeat().getBasePrice());
                    showSeatDTO.setSeat(baseSeatDTO);
                    return showSeatDTO;
                })
                .collect(Collectors.toList());

         showDTO.setAvailableSeats(showSeats);

         return showDTO;
    }
}
