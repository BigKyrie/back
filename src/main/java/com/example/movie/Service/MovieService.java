package com.example.movie.Service;
import java.util.ArrayList;
import java.util.Date;

import com.example.movie.Entity.*;
import com.example.movie.Repository.Cinema_AdminRepository;
import com.example.movie.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    private ScreeningService screeningService;

    public void deletebyID(Integer id){
        movieRepository.deleteByID(id);
    }

    public Movie findMoviebyID(Integer id){
        return movieRepository.findMovieByID(id);
    }
    public List<Movie> display_all_movies(){
        return movieRepository.display_all_movies();
    }

    public List<Movie> display_movies_by_adminID(Integer id){
        Cinema_Admin cinema_admin = cinema_adminService.findAdminById(id);
        List<Screen> screens = screenService.search_screen_by_cinema(cinema_admin.getCinema().getId());
        List<Screening> screenings = new ArrayList<>();
        for(Screen screen:screens){
            screenings.addAll(screeningService.find_screenings_by_screen_id(screen.getId()));
        }
        List<Movie> movies=new ArrayList<>();
        for(Screening screening:screenings){
            int temp=0;
            for(Movie movie:movies){
                if (screening.getMovie().getId().equals(movie.getId())) {
                    temp = 1;
                    break;
                }
            }
            if(temp==0){
                movies.add(screening.getMovie());
            }
        }
        return movies;
    }

    public boolean addMovies(String title,String blurb,String certificate,String director,String actors,
                             Date showtime,Integer duration,String type,String language,String url) {

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setBlurb(blurb);
        movie.setCertificate(certificate);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setShowtime(showtime);
        movie.setDuration(duration);
        movie.setType(type);
        movie.setLanguage(language);
        movie.setUrl(url);
        movieRepository.save(movie);
        return true;
    }

}
