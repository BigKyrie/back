package com.example.movie.Repository;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie,Integer>
{
    @Query(value = "select * from movie" ,nativeQuery = true)
    List<Movie> display_all_movies();
}
