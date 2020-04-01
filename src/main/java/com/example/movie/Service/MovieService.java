package com.example.movie.Service;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.User;
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

    public List<Movie> display_all_movies(){
        return movieRepository.display_all_movies();
    }

    public boolean addMovies(String title, String blurb, String certificate, String director, String actors,
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
        return true;
    }

}
