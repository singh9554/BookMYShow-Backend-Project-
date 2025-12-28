package com.cfs.org.BMS.Repository;
import java.util.*;
import com.cfs.org.BMS.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionID(String transactionID);
}
