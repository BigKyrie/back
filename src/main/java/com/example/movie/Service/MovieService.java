package com.example.movie.Service;
import java.util.Date;
import com.example.movie.Entity.Movie;
import com.example.movie.Entity.User;
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

    public void deletebyID(Integer id){
        movieRepository.deleteByID(id);
    }

    public Movie findMoviebyID(Integer id){
        return movieRepository.findMovieByID(id);
    }
    public List<Movie> display_all_movies(){
        return movieRepository.display_all_movies();
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

//    public boolean add() {
//        List<User> findUserName = userRepository.findByUsername(username);
//        if (findUserName.size() != 0) {
//            return false;
//        } else {
//            if (username.length() > 20 || username.length() == 0 || password.length() > 20 || password.length() == 0) {
//                return false;
//            } else {
//                User user = new User();
//                user.setUsername(username);
//                user.setPassword(password);
//                userRepository.save(user);
//                return true;
//            }
//        }
//    }



}
