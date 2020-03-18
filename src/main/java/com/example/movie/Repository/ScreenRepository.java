package com.example.movie.Repository;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Screen;
import com.example.movie.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends CrudRepository<Screen,Integer>
{
    @Query(value = "select * from screen where id=? GROUP BY id",nativeQuery = true)
    List<Screen> search_screen_by_screening(Integer id);
}