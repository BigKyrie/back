package com.example.movie.Repository;

import com.example.movie.Entity.Ticket;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket,Integer> {
    @Query(value = "select * from ticket where screening_id = ?", nativeQuery = true)
    List<Ticket> find_ticket_of_a_screening(Integer id);
    @Query(value = "select * from ticket where screening_id = ?", nativeQuery = true)
    List<Ticket> find_ticket_by_screening_and_seat(Integer screening_id,Integer seat_id);
    @Query(value = "select * from ticket where user_id=?", nativeQuery = true)
    List<Ticket> find_ticket_by_userid(Integer user_id);
    @Query(value = "select * from ticket where id = ?", nativeQuery = true)
    List<Ticket> find_ticket_by_id(Integer id);
    @Modifying
    @Query(value = "delete from ticket where id = ?",nativeQuery = true)  //may delete
    public void delete_by_ticket_id(Integer id);
}
