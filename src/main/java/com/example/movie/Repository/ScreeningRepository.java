package com.example.movie.Repository;

import com.example.movie.Entity.Screening;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScreeningRepository extends CrudRepository<Screening,Integer> {
    //find screening by movie_id
    @Query(value = "select * from screening where movie_id = ?",nativeQuery = true)
    public List<Screening> display_screenings_by_movie_id(Integer id);

    @Query(value = "select * from screening where screen_id = ?",nativeQuery = true)
    public List<Screening> find_screenings_by_screen_id(Integer screen_id);

    @Query(value = "select * from screening where id = ?", nativeQuery = true)
    public List<Screening> find_screening_by_screening_id(Integer id);


    @Modifying //may delete
    @Query(value = "delete from screening where movie_id = ?",nativeQuery = true)  //may delete
    public void deleteBymovieID(Integer id);  //may delete

}
