package com.cfs.org.BMS.Model;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private Integer durationMins;

    @Column(nullable = false)
    private String releaseDate;

    @Column(nullable = false)
    private String PosterUrl;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Show> show;
}
