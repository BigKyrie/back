package com.example.movie.Repository;

import com.example.movie.Entity.Seat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat,Integer> {
    @Query(value = "select * from seat where screen_id = ?",nativeQuery = true)
    public List<Seat> view_seat_of_a_screen(Integer id);
    @Query(value = "select * from seat where id = ?",nativeQuery = true)
    public List<Seat> find_seat_by_id(Integer id);
    @Modifying
    @Query(value = "delete from seat where id = ?",nativeQuery = true)  //may delete
    void delete_by_seat_id(Integer id);
}
