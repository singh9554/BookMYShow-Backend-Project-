package com.cfs.org.BMS.Repository;
import com.cfs.org.BMS.Model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {

    List<ShowSeat> findByShowID(Long movieID);
    List<ShowSeat> findByShowIDAndStatus(Long showID, String Status);
}
