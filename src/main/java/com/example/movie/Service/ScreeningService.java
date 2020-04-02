package com.example.movie.Service;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Screen;
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
        List <Screening> screenings_by_movie_id = screeningRepository.display_screenings_by_movie_id(movie_id);
        List <Screening> screenings_by_movie_id_and_cinema_id = new ArrayList<>();
        for (int i = 0; i< screenings_by_movie_id.size();i++) {
           if (screenings_by_movie_id.get(i).getScreen().getCinema().getId() == cinema_id)
               screenings_by_movie_id_and_cinema_id.add(screenings_by_movie_id.get(i));
        }
        return screenings_by_movie_id_and_cinema_id;
    }

    public Screening find_screening_by_screening_id(Integer id){
        return screeningRepository.find_screening_by_screening_id(id).get(0);
    }
    public boolean addScreening(Date start_time, Date end_time, float price, Screen screen, Movie movie) {
        Screening screening=new Screening();
        screening.setStart_time(start_time);
        screening.setEnd_time(end_time);
        screening.setPrice(price);
        screening.setMovie(movie);
        screening.setScreen(screen);
        screeningRepository.save(screening);
        return true;
    }

}
