package com.example.movie.Service;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Screen;
import com.example.movie.Entity.Screening;
import com.example.movie.Repository.ScreenRepository;
import com.example.movie.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang3.time.DateUtils;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.time.DateUtils.isSameDay;

@Transactional
@Service
public class ScreeningService {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private ScreenRepository screenRepository;

    public List<Screening> find_screenings_by_screen_id(Integer id){
        return screeningRepository.find_screenings_by_screen_id(id);
    }

    public void deletebymovieID(Integer id){
        screeningRepository.deleteBymovieID(id);
    }

    public List<Screening> display_screenings_by_movie_id(Integer id){
        return screeningRepository.display_screenings_by_movie_id(id);
    }
    public List<Screening> display_screenings_filterby_cinema_id_and_movie_id(Integer movie_id, Integer cinema_id){
        List <Screening> screenings_by_movie_id = screeningRepository.display_screenings_by_movie_id(movie_id);
        List <Screening> screenings_by_movie_id_and_cinema_id = new ArrayList<>();
        for (int i = 0; i< screenings_by_movie_id.size();i++) {
           if (screenings_by_movie_id.get(i).getScreen().getCinema().getId() == cinema_id)
               screenings_by_movie_id_and_cinema_id.add(screenings_by_movie_id.get(i));
        }
        return screenings_by_movie_id_and_cinema_id;
    }
    public void delete_by_screening_id(Integer id) {
        screeningRepository.delete_by_screening_id(id);
    }


    public void update(Date start_time, Date end_time, float price, Screen screen, Movie movie,Integer screening_id) {
        Screening screening=screeningRepository.find_screening_by_screening_id(screening_id).get(0);
        screening.setStart_time(start_time);
        screening.setEnd_time(end_time);
        screening.setPrice(price);
        screening.setScreen(screen);
        screening.setMovie(movie);
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

    public List<Screening> screenings_in_cinema(Integer cinema_id) {
        List<Screen> screensInTheCinema=screenRepository.search_screen_by_cinema(cinema_id);
        List<Screening> final_screenings = new ArrayList<>();
        for(int i=0;i<screensInTheCinema.size();i++) {
            List<Screening> screenings=screeningRepository.find_screenings_by_screen_id(screensInTheCinema.get(i).getId());
            for(int j=0;j<screenings.size();j++) {
                if(!final_screenings.contains(screenings.get(j))) {
                    final_screenings.add(screenings.get(j));
                }
            }
        }
        return final_screenings;
    }
    public List<Screening> searchScreening(String screen_number, String movie_title, String date,Integer cinema_id) {
        List<Screening> final_screenings = new ArrayList<>();
        List<Screening> screenings_in_cinema=this.screenings_in_cinema(cinema_id);
        if(screen_number.equals("all") || screen_number.equals("All") || screen_number.equals("ALL")) {
            if(movie_title.equals("all") || movie_title.equals("All") || movie_title.equals("ALL")) {
                if(date.equals("all") || date.equals("All") || date.equals("ALL")) {
                    //case 1    three all
                    for(int i=0;i<screenings_in_cinema.size();i++) {
                        final_screenings.add(screenings_in_cinema.get(i));
                    }
                }
                else {
                    //case 2    screen&movie all
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    try{
                        Date selectedDate=sdf.parse(date);
                        for(int i=0;i<screenings_in_cinema.size();i++) {
                            if(isSameDay(screenings_in_cinema.get(i).getStart_time(), selectedDate)) {
                                final_screenings.add(screenings_in_cinema.get(i));
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                if(date.equals("all") || date.equals("All") || date.equals("ALL")) {
                    //case 3   screen&date all
                    for(int i=0;i<screenings_in_cinema.size();i++) {
                        if(screenings_in_cinema.get(i).getMovie().getTitle().equals(movie_title)) {
                            final_screenings.add(screenings_in_cinema.get(i));
                        }
                    }
                }
                else {
                    //case 4   screen all
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date selectedDate=sdf.parse(date);
                        for(int i=0;i<screenings_in_cinema.size();i++) {
                            if(screenings_in_cinema.get(i).getMovie().getTitle().equals(movie_title) &&
                                    isSameDay(screenings_in_cinema.get(i).getStart_time(), selectedDate)) {
                                final_screenings.add(screenings_in_cinema.get(i));
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            }

        } else {
            if(movie_title.equals("all") || movie_title.equals("All") || movie_title.equals("ALL")) {
                if(date.equals("all") || date.equals("All") || date.equals("ALL")) {
                    //case 5    movie&date all
                    for(int i=0;i<screenings_in_cinema.size();i++) {
                        if(screenings_in_cinema.get(i).getScreen().getNum()==Integer.parseInt(screen_number)) {
                            final_screenings.add(screenings_in_cinema.get(i));
                        }
                    }
                }
                else {
                    //case 6    movie all
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date selectedDate=sdf.parse(date);
                        for(int i=0;i<screenings_in_cinema.size();i++) {
                            if(screenings_in_cinema.get(i).getScreen().getNum()==Integer.parseInt(screen_number) &&
                                    isSameDay(screenings_in_cinema.get(i).getStart_time(), selectedDate)) {
                                final_screenings.add(screenings_in_cinema.get(i));
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            }
            else {
                if(date.equals("all") || date.equals("All") || date.equals("ALL")) {
                    //case 7    date all
                    for(int i=0;i<screenings_in_cinema.size();i++) {
                        if(screenings_in_cinema.get(i).getScreen().getNum()==Integer.parseInt(screen_number) &&
                                screenings_in_cinema.get(i).getMovie().getTitle().equals(movie_title)) {
                            final_screenings.add(screenings_in_cinema.get(i));
                        }
                    }
                }
                else {
                    //case 8    no all
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date selectedDate=sdf.parse(date);
                        for(int i=0;i<screenings_in_cinema.size();i++) {
                            if(screenings_in_cinema.get(i).getScreen().getNum()==Integer.parseInt(screen_number) &&
                                    isSameDay(screenings_in_cinema.get(i).getStart_time(), selectedDate) &&
                                    screenings_in_cinema.get(i).getMovie().getTitle().equals(movie_title)) {
                                final_screenings.add(screenings_in_cinema.get(i));
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
        return final_screenings;
    }
}
