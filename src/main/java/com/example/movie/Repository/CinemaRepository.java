package com.example.movie.Repository;
import com.example.movie.Entity.Cinema;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema,Integer>
{
    @Query(value = "select * from cinema" ,nativeQuery = true)
    List<Cinema> display_all_cinemas();

    @Query(value = "select * from cinema where id=? GROUP BY id",nativeQuery = true)
    List<Cinema> display_cinema_by_id(Integer id);

    @Query(value="select * from cinema where location = ?", nativeQuery = true)
    List<Cinema> find_cinema_by_location(String location);
}