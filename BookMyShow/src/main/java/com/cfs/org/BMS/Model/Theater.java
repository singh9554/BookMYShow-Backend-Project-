package com.cfs.org.BMS.Model;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="theater")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private  String city;

    @Column(nullable = false)
    private Integer totalScreen;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<Screen> screen;
}
