package com.example.movie.Repository;

import com.example.movie.Entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card,Integer> {

    @Query(value = "select * from card where card_number = ?", nativeQuery = true)
    List<Card> find_card_by_number(String card_number);

    @Query(value = "select * from card where user_id = ?", nativeQuery = true)
    List<Card> find_card_by_user_id(Integer user_id);
}
