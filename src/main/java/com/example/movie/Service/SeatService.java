package com.example.movie.Service;

import com.example.movie.Entity.Screen;
import com.example.movie.Entity.Seat;
import com.example.movie.Repository.ScreenRepository;
import com.example.movie.Repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ScreenRepository screenRepository;

    public List<Seat> view_seat_of_a_screen(Integer id){return seatRepository.view_seat_of_a_screen(id);}

    public Seat find_seat_by_id(Integer id) {
        return seatRepository.find_seat_by_id(id).get(0);
    }

    public boolean add_seats(Integer screen_id, int row, int col){
        Screen screen = screenRepository.search_screen_by_id(screen_id).get(0);
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                Seat seat = new Seat();
                seat.setCol(Integer.valueOf(i+1));
                seat.setRow(Integer.valueOf(j+1));
                seat.setScreen(screen);
                seatRepository.save(seat);
            }
        }
        return true;
    }

}
