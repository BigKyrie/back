package com.example.movie.Repository;

import com.example.movie.Entity.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket,Integer> {
    @Query(value = "select * from ticket where screening_id = ?", nativeQuery = true)
    List<Ticket> find_ticket_of_a_screening(Integer id);
    @Query(value = "select * from ticket where screening_id = ?", nativeQuery = true)
    List<Ticket> find_ticket_by_screening_and_seat(Integer screening_id,Integer seat_id);
}
