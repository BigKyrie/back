package com.example.movie.Repository;

import com.example.movie.Entity.Screening;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScreeningRepository extends CrudRepository<Screening,Integer> {
    @Query(value = "select * from screening where movie_id = ?",nativeQuery = true)
    public List<Screening> display_screenings_by_id(Integer id);
}
