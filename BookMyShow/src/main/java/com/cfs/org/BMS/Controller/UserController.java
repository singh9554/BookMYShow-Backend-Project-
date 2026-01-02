package com.cfs.org.BMS.Controller;

import com.cfs.org.BMS.DTO.UserDTO;
import com.cfs.org.BMS.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.createUser(userDTO),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long Id){

        return ResponseEntity.ok(userService.getUserById(Id));
    }
}
