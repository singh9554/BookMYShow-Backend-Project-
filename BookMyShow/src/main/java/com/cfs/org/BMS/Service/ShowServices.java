package com.cfs.org.BMS.Service;

import com.cfs.org.BMS.Repository.MovieRepository;
import com.cfs.org.BMS.Repository.ScreenRepository;
import com.cfs.org.BMS.Repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowServices {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;
}
