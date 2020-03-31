package com.example.movie.Repository;

import com.example.movie.Entity.Screen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends CrudRepository<Screen,Integer>
{
    @Query(value = "select * from screen where id=? GROUP BY id",nativeQuery = true)
    List<Screen> search_screen_by_id(Integer id);

    @Query(value = "select * from screen where cinema_id=?",nativeQuery = true)
    List<Screen> search_screen_by_cinema(Integer id);

    @Query(value = "select * from screen",nativeQuery = true)
    List<Screen> display__all_screens();
}