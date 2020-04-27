package com.example.movie.Repository;

import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Movie;
import com.example.movie.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie,Integer>
{
    @Query(value = "select * from movie" ,nativeQuery = true)
    List<Movie> display_all_movies();

    @Query(value = "select * from movie where id=?",nativeQuery = true)
    public Movie findMovieByID(Integer id);

    @Modifying //may delete
    @Query(value = "delete from movie where id = ?",nativeQuery = true)  //may delete
    public void deleteByID(Integer id);  //may delete
    @Query(value = "select * from movie where certificate=?" ,nativeQuery = true)
    List<Movie> find_movie_by_certificate(String certificate);

    @Query(value = "select * from movie where title like ?",nativeQuery = true)
    public List<Movie> search_movie_by_keyword(String keyword);



}
