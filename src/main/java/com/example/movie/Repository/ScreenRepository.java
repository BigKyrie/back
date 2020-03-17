package com.example.movie.Repository;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Screen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScreenRepository extends CrudRepository<Screen,Integer> {
    @Query(value = "select * from screen" ,nativeQuery = true)
    List<Screen> display_all_screens();
}
