package com.cfs.org.BMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDTO {
    private Long id;
    private String name;
    private String address;
    private String City;
    private Integer totalScreens;
}
