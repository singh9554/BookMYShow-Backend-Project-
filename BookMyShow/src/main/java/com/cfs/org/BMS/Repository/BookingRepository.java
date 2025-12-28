package com.cfs.org.BMS.Repository;
import java.util.List;
import java.util.Optional;

import com.cfs.org.BMS.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    List<Booking> findByUserID(Long userId);

    Optional<Booking> findByBookingNumber(String bookingNumber);

    List<Booking> findByShowID(Long id);
}
