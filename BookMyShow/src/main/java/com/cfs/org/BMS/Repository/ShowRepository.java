package com.cfs.org.BMS.Repository;
import com.cfs.org.BMS.Model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.*;
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieID(Long MovidID);
    List<Show> findByScreenID(Long ScreenID);
    List<Show> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Show> findByMovie_IDAndScreen_Theater_city(Long movieID, String city);
}
