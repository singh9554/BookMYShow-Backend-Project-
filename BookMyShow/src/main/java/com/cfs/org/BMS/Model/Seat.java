package com.cfs.org.BMS.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String SeatNumber;

    @Column(nullable = false)
    private String SeatType;

    @Column(nullable = false)
    private Double basePrice;

    @ManyToOne
    @JoinColumn(name="screen_id", nullable = false)
    private Screen screen;
}
