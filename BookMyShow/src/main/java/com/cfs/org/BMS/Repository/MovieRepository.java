package com.cfs.org.BMS.Repository;
import java.util.*;
import com.cfs.org.BMS.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByLanguage(String Language);

    List<Movie> findByGenre(String genre);

    List<Movie> findByTitleContaining(String title);
}