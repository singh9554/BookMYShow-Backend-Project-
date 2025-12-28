package com.cfs.org.BMS.Repository;
import com.cfs.org.BMS.Model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findByTheaterID(Long TheaterID);
}
