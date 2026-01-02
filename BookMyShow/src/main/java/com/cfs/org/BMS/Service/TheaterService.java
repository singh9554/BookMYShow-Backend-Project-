package com.cfs.org.BMS.Service;

import com.cfs.org.BMS.DTO.TheaterDTO;
import com.cfs.org.BMS.Exception.ResourceNotFoundException;
import com.cfs.org.BMS.Model.Theater;
import com.cfs.org.BMS.Repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public TheaterDTO createTheater(TheaterDTO theaterDTO){

        Theater theater = mapToEntity(theaterDTO);

        Theater saveTheater = theaterRepository.save(theater);

        return mapToDto(saveTheater);
    }
    //Get by Id;
    public TheaterDTO getTheaterById(Long Id){

        Theater theater = theaterRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Theater not found by id: "+Id));

        return mapToDto(theater);
    }

    //get all theater
    public List<TheaterDTO> getAllTheater(){

        List<Theater> theater = theaterRepository.findAll();

        return theater.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    //get by city
    private List<TheaterDTO> getAllTheaterByCity(String city){
        List<Theater> theaters = theaterRepository.findByCity(city);

        return theaters.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    //update Theater
    private TheaterDTO updateTheater(TheaterDTO theaterDTO,Long Id) {
        Theater theater = theaterRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Theater not found by id: "+Id));

        theater.setName(theaterDTO.getName());
        theater.setAddress(theaterDTO.getAddress());
        theater.setCity(theaterDTO.getCity());
        theater.setTotalScreen(theaterDTO.getTotalScreens());

       Theater updatedTheater =  theaterRepository.save(theater);

       return mapToDto(updatedTheater);
    }
    //delete Theater
    private void deleteTheater(Long Id){

        Theater theater = theaterRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Theater not found by id: "+Id));

        theaterRepository.delete(theater);
    }
    //map to Dto;
    private TheaterDTO mapToDto(Theater theater){

        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setId(theater.getID());
        theaterDTO.setName(theater.getName());
        theaterDTO.setAddress(theater.getAddress());
        theaterDTO.setCity(theater.getCity());
        theaterDTO.setTotalScreens(theater.getTotalScreen());

        return theaterDTO;
    }

    //map to Entity;
    private Theater mapToEntity(TheaterDTO theaterDTO){

        Theater theater = new Theater();
        theater.setID(theaterDTO.getId());
        theater.setName(theaterDTO.getName());
        theater.setAddress(theaterDTO.getAddress());
        theater.setCity(theaterDTO.getCity());
        theater.setTotalScreen(theaterDTO.getTotalScreens());

        return theater;
    }
}
