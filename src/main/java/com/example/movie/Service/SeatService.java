package com.example.movie.Service;

import com.example.movie.Entity.Screen;
import com.example.movie.Entity.Screening;
import com.example.movie.Entity.Seat;
import com.example.movie.Entity.Ticket;
import com.example.movie.Repository.ScreenRepository;
import com.example.movie.Repository.SeatRepository;
import com.example.movie.nonEntity.SeatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Transactional
@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private TicketService ticketService;

    public List<Seat> view_seat_of_a_screen(Integer id){return seatRepository.view_seat_of_a_screen(id);}

    public Seat find_seat_by_id(Integer id) {
        return seatRepository.find_seat_by_id(id).get(0);
    }

    public boolean add_seats(Integer screen_id, int row, int col){
        Screen screen = screenRepository.search_screen_by_id(screen_id).get(0);
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                Seat seat = new Seat();
                seat.setRow(Integer.valueOf(i+1));
                seat.setCol(Integer.valueOf(j+1));
                seat.setScreen(screen);
                seatRepository.save(seat);
            }
        }
        return true;
    }

    public List<SeatInfo> get_seat_of_a_screening(Integer screening_id){
        Screening screening = screeningService.find_screening_by_screening_id(screening_id);
        List<Seat> seats =this.view_seat_of_a_screen(screening.getScreen().getId());
        List<Ticket> tickets = ticketService.find_ticket_of_a_screening(screening_id);
        List<SeatInfo> seatInfos = new ArrayList<>();
        List<Seat> taken = new ArrayList<>();
        List<Seat> empty = new ArrayList<>();
        for (int i = 0; i< tickets.size();i++) {
            for (int j = 0; j < seats.size();j++){
                if (tickets.get(i).getSeat().getId() == seats.get(j).getId()){
                    taken.add(seats.get(j));
                    seats.remove(seats.get(j));
                }
            }
        }
        for (int i = 0; i < taken.size();i++){
            SeatInfo seatInfo = new SeatInfo();
            seatInfo.setCol(taken.get(i).getCol());
            seatInfo.setRow(taken.get(i).getRow());
            seatInfo.setSeat_id(taken.get(i).getId());
            seatInfo.setScreening_id(screening.getId());
            seatInfo.setTaken_or_not(true);
            seatInfos.add(seatInfo);
        }
        for (int i = 0; i < seats.size();i++){
            SeatInfo seatInfo = new SeatInfo();
            seatInfo.setCol(seats.get(i).getCol());
            seatInfo.setRow(seats.get(i).getRow());
            seatInfo.setSeat_id(seats.get(i).getId());
            seatInfo.setScreening_id(screening.getId());
            seatInfo.setTaken_or_not(false);
            seatInfos.add(seatInfo);
        }
        Collections.sort(seatInfos, new Comparator<SeatInfo>() {
            @Override
            public int compare(SeatInfo o1, SeatInfo o2) {
                if (o1.getRow() - o2.getRow() == 0)
                    return o1.getCol().compareTo(o2.getCol());
                else
                    return o1.getRow()-o2.getRow();
            }
        });
        return seatInfos;
    }
}

