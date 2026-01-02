package com.cfs.org.BMS.Service;


import com.cfs.org.BMS.DTO.UserDTO;
import com.cfs.org.BMS.Exception.ResourceNotFoundException;
import com.cfs.org.BMS.Model.User;
import com.cfs.org.BMS.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Create User;
    public UserDTO createUser(UserDTO userDto){

        User user = mapToEntity(userDto);
        User saveUser = userRepository.save(user);

        return mapToDto(saveUser);
    }

    //get by Id;
    public  UserDTO getUserById(Long Id){

        User user = userRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found by Id: "+Id));

        return mapToDto(user);
    }
    //find all user
    public List<UserDTO> getAllUser(){
        List<User> user = userRepository.findAll();

        return user.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    //update User
    private UserDTO updateUser(UserDTO userDTO,Long Id){
        User user = userRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found by Id: "+Id));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        User saveUser = userRepository.save(user);
        return mapToDto(saveUser);
    }
    //Delete user
    private void deleteUser(Long Id){
        User user = userRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found by Id: "+Id));
        userRepository.delete(user);
    }
    //map to Entity
    private User mapToEntity(UserDTO userDTO){
        User user = new User();
        user.setID(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassWord());
        return user;
    }

    private UserDTO mapToDto(User user){

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getID());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        return userDTO;
    }
}
