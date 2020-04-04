package com.example.movie.Service;

import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Screen;
import com.example.movie.Repository.CinemaRepository;
import com.example.movie.Repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ScreenService {
    @Autowired
    private ScreenRepository screenRepository;

    public List<Screen> display_all_screens(){
        return screenRepository.display_all_screens();
    }

    public List<Screen> get_screen_by_id(Integer id){ return screenRepository.search_screen_by_id(id);}

    public Screen find_screen_by_num_and_cinema(Integer num,Integer cinema_id) {
        return screenRepository.find_screen_by_num_and_cinema(num,cinema_id).get(0);
    }


}
