package com.cfs.org.BMS.Controller;

import com.cfs.org.BMS.DTO.ShowDTO;
import com.cfs.org.BMS.Service.ShowServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    ShowServices showServices;

    @PostMapping
    public ResponseEntity<ShowDTO> CreateShow(@Valid @RequestBody ShowDTO showDTO){
        return new ResponseEntity<>(showServices.createShow(showDTO), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ShowDTO>> getAllShow(){
        return ResponseEntity.ok(showServices.getAllShow());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ShowDTO> getShowById(@PathVariable Long Id){
        return ResponseEntity.ok(showServices.getShowById(Id));
    }
}
