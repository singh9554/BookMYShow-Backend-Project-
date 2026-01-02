package com.cfs.org.BMS.Service;

import com.cfs.org.BMS.DTO.MovieDTO;
import com.cfs.org.BMS.Exception.ResourceNotFoundException;
import com.cfs.org.BMS.Model.Movie;
import java.util.List;
import java.util.stream.Collectors;

import com.cfs.org.BMS.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServices {

    @Autowired
    private MovieRepository movieRepository;

    //CreateMovie
    public MovieDTO createMovie(MovieDTO movieDTO){

        Movie movie = mapToEntity(movieDTO);
        Movie saveMove = movieRepository.save(movie);

        return mapToDTO(saveMove);
    }

    //getMovie
    public MovieDTO getMovieById(Long id){

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not found by Id: "+ id));

         return mapToDTO(movie);
    }

    public List<MovieDTO> getAllMovies(){

        List<Movie> movie = movieRepository.findAll();

        return movie.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> getMovieByLanguage(String language){
        List<Movie> movie = movieRepository.findByLanguage(language);

        return movie.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    public List<MovieDTO> getMovieByGenre(String genre){
        List<Movie> movie = movieRepository.findByGenre(genre);

        return movie.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    //updateMovie
    public MovieDTO UpdateMovie(Long id, MovieDTO movieDTO){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not found by Id: "+ id));

        movie.setID(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setGenre(movieDTO.getGenre());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setDescription(movieDTO.getDescription());
        movie.setDurationMins(movieDTO.getDurationMins());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setPosterUrl(movieDTO.getPosterUrl());

        Movie updatedMovie = movieRepository.save(movie);

        return mapToDTO(updatedMovie);
    }
    //Delete Movie
    public void deleteMovie(Long Id){

        Movie movie = movieRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("No movie found to delete with this id: "+ Id));

        movieRepository.delete(movie);
    }
    //MapToDTO
    private MovieDTO mapToDTO(Movie movie){

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movie.getID());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setLanguage(movie.getLanguage());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setDurationMins(movie.getDurationMins());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setPosterUrl(movie.getPosterUrl());

        return movieDTO;
    }

  //MapToEntity
    private Movie mapToEntity(MovieDTO movieDTO){

        Movie movie = new Movie();

        movie.setID(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setGenre(movieDTO.getGenre());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setDescription(movieDTO.getDescription());
        movie.setDurationMins(movieDTO.getDurationMins());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setPosterUrl(movieDTO.getPosterUrl());

        return movie;
    }
}
