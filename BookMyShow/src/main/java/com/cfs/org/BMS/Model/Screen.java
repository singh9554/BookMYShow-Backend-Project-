package com.cfs.org.BMS.Model;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="screen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  Integer TotalSeats;

    @ManyToOne
    @JoinColumn(name="theater_id", nullable = false)
    private Theater theater;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    private List<Show> show;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    private List<Seat> seat;
}
