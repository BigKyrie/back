package com.example.movie.Service;

import com.example.movie.Entity.*;
import com.example.movie.Repository.MovieRepository;
import com.example.movie.Repository.ScreenRepository;
import com.example.movie.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ScreeningRepository screeningRepository;
    @Autowired
    private ScreenRepository screenRepository;

    public List<Movie> display_all_movies(){
        return movieRepository.display_all_movies();
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
                finalScreenings.add(screenings.get(j));
            }

        }
        for(int i=0;i<finalScreenings.size();i++) {
            if(!moviesInCinema.contains(finalScreenings.get(i).getMovie())) {
                moviesInCinema.add(finalScreenings.get(i).getMovie());
            }
        }
        return moviesInCinema;
    }


}
