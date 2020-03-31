package com.example.movie.Repository;

import com.example.movie.Entity.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat,Integer> {
    @Query(value = "select * from seat where screen_id = ?",nativeQuery = true)
    public List<Seat> view_seat_of_a_screen(Integer id);
}
