package com.example.movie.Service;

import com.example.movie.Entity.Movie;
import com.example.movie.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> display_all_movies(){
        return movieRepository.display_all_movies();
    }
}
