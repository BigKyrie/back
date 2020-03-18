package com.example.movie.Service;

import com.example.movie.Entity.Screening;
import com.example.movie.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ScreeningService {
    @Autowired
    private ScreeningRepository screeningRepository;
    public List<Screening> display_screenings_filterby_cinema_id_and_movie_id(Integer movie_id, Integer cinema_id){
        List <Screening> screenings_by_movie_id = screeningRepository.display_screenings_by_id(movie_id);
        List <Screening> screenings_by_movie_id_and_cinema_id = new ArrayList<>();
        for (int i = 0; i< screenings_by_movie_id.size();i++) {
           if (screenings_by_movie_id.get(i).getScreen().getCinema().getId() == cinema_id)
               screenings_by_movie_id_and_cinema_id.add(screenings_by_movie_id.get(i));
        }
        return screenings_by_movie_id_and_cinema_id;
    }
}