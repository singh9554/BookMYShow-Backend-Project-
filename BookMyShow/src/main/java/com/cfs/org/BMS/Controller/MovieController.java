package com.cfs.org.BMS.Controller;

import com.cfs.org.BMS.DTO.BookingDTO;
import com.cfs.org.BMS.DTO.BookingRequestDTO;
import com.cfs.org.BMS.DTO.MovieDTO;
import com.cfs.org.BMS.Service.MovieServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    MovieServices movieServices;

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO){
        return new ResponseEntity<>(movieServices.createMovie(movieDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<MovieDTO> getBooking(@PathVariable Long Id){
        return ResponseEntity.ok(movieServices.getMovieById(Id));
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies(){
        return ResponseEntity.ok(movieServices.getAllMovies());
    }
}
