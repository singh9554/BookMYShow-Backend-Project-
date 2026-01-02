package com.cfs.org.BMS.Controller;

import com.cfs.org.BMS.DTO.TheaterDTO;
import com.cfs.org.BMS.Service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theater")
public class TheaterController {
    @Autowired
    TheaterService theaterService;

    @PostMapping
    public ResponseEntity<TheaterDTO> createTheater(@Valid @RequestBody TheaterDTO theaterDTO){
        return new ResponseEntity<>(theaterService.createTheater(theaterDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TheaterDTO>> getAllTheater(){
        return ResponseEntity.ok(theaterService.getAllTheater());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<TheaterDTO> getTheaterById(@PathVariable Long Id){
        return ResponseEntity.ok(theaterService.getTheaterById(Id));
    }
}
