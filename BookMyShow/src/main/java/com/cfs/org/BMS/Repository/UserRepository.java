package com.cfs.org.BMS.Repository;
import java.util.*;
import com.cfs.org.BMS.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);
    Boolean existingByEmail(String email);
}
