package com.example.movie.Service;

import com.example.movie.Entity.*;
import com.example.movie.Repository.CinemaRepository;
import com.example.movie.Repository.MovieRepository;
import com.example.movie.Repository.ScreenRepository;
import com.example.movie.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CinemaService {
    @Autowired
    private ScreeningRepository screeningRepository;
    private CinemaRepository cinemaRepository;
    private ScreenRepository screenRepository;


//    public List<Cinema> display_all_cinemas(){
//        return cinemaRepository.display_all_cinemas();
//    }

    public List<Cinema> display_cinemas_for_a_certain_movie(Integer id) {
        List<Cinema> cinemas=new ArrayList<>();
        List<Screen> screens=new ArrayList<>();
        List<Cinema> final_cinemas=new ArrayList<>();
        List<Screening> screenings = screeningRepository.display_screenings_by_id(id);
        for (int i = 0; i < screenings.size(); i++) {
            screens.add(screenings.get(i).getScreen());
            for (int j = 0; j < screens.size(); j++) {
                cinemas.add(screens.get(j).getCinema());
            }
        }
        for (int i = 0; i < cinemas.size(); ++i) {
            int a=0;
            for(int j = 0; j < final_cinemas.size(); ++j) {
                if(cinemas.get(i)==final_cinemas.get(j)) {
                    a=1;
                }
            }
            if(a==0) {
                final_cinemas.add(cinemas.get(i));
            }
        }
        return final_cinemas;
    }
}
