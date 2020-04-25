package com.example.movie.Service;

import com.example.movie.Entity.*;
import com.example.movie.Repository.CardRepository;
import com.example.movie.nonEntity.MovieSelling;
import com.example.movie.Entity.Screening;
import com.example.movie.Entity.Seat;
import com.example.movie.Entity.Ticket;
import com.example.movie.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

@Transactional
@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public List<Card> find_card_by_number(String card_number) {
        return cardRepository.find_card_by_number(card_number);
    }
    public List<Card> find_card_by_user_id(Integer user_id) {
        return cardRepository.find_card_by_user_id(user_id);
    }
    public void add(String card_number,String password,Cinema_Admin cinema_admin) {
        Card card=new Card();
        card.setNumber(card_number);
        card.setPassword(password);
        card.setCinema_admin(cinema_admin);
        cardRepository.save(card);
    }



}
