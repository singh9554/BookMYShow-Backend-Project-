package com.cfs.org.BMS.Repository;
import com.cfs.org.BMS.Model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    List<Theater> findByShowID(String city);
}
