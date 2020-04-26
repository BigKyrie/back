package com.example.movie.Service;
import java.util.ArrayList;
import java.util.Date;

import com.example.movie.Entity.*;
import com.example.movie.Repository.MovieRepository;
import com.example.movie.Repository.ScreenRepository;
import com.example.movie.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    @Autowired
    private TicketService ticketService;

    public void deletebyID(Integer id){
        movieRepository.deleteByID(id);
    }
    private ScreeningRepository screeningRepository;
    @Autowired
    private ScreenRepository screenRepository;
    public Movie findMoviebyID(Integer id){
        return movieRepository.findMovieByID(id);
    }
    public List<Movie> display_all_movies(){
        return movieRepository.display_all_movies();
    }

    public List<Movie> display_movies_by_userID(Integer id){
        Cinema_Admin cinema_admin = cinema_adminService.findAdminById(id);
        List<Ticket> tickets = ticketService.find_ticket_of_a_user(cinema_admin.getId());
        List<Movie> movies = new ArrayList<>();
        List<Movie> final_movies = new ArrayList<>();
        for(Ticket ticket:tickets){
            movies.add(findMoviebyID(ticket.getScreening().getMovie().getId()));
        }

        for(Movie movie:movies){
            if(final_movies.contains(movie)){
            }
            else{
                final_movies.add(movie);
            }
        }

        return final_movies;

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

    public List<Movie> find_movie_by_certificate(String certificate) {
        return movieRepository.find_movie_by_certificate(certificate);
    }

    public Movie addMovies(String title, String blurb, String certificate, String director, String actors,
                             Date showtime, Integer duration, String type, String language, String url) {
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
        return movie;
    }

    public List<Movie> movies_in_cinema(Integer cinema_id) {
        List<Screen> screensInTheCinema=screenRepository.search_screen_by_cinema(cinema_id);
        List<Screening> finalScreenings=new ArrayList<>();
        List<Movie> moviesInCinema=new ArrayList<>();
        for(int i=0;i<screensInTheCinema.size();i++) {
            List<Screening> screenings=screeningRepository.find_screenings_by_screen_id(screensInTheCinema.get(i).getId());
            for(int j=0;j<screenings.size();j++) {
                if(!finalScreenings.contains(screenings.get(j))) {
                    finalScreenings.add(screenings.get(j));
                }
            }

        }
        for(int i=0;i<finalScreenings.size();i++) {
            if(!moviesInCinema.contains(finalScreenings.get(i).getMovie())) {
                moviesInCinema.add(finalScreenings.get(i).getMovie());
            }
        }
        return moviesInCinema;
    }
    public void update(String title, String blurb, String certificate, String director, String actors,
                       Date showtime, Integer duration, String type, String language, String url, Integer movie_id) {
        Movie movie=movieRepository.findMovieByID(movie_id);
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
    }





}
