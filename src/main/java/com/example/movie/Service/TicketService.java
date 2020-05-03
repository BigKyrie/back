package com.example.movie.Service;

import com.example.movie.Entity.*;
import com.example.movie.nonEntity.MovieSelling;
import com.example.movie.Entity.Screening;
import com.example.movie.Entity.Seat;
import com.example.movie.Entity.Ticket;
import com.example.movie.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


@Transactional
@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    private UserService userService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private ScreeningService screeningService;

    public Ticket find_ticket_by_id(Integer id) {
        return ticketRepository.find_ticket_by_id(id).get(0);
    }

    public List<Ticket> find_ticket_of_a_screening(Integer id) {
        return ticketRepository.find_ticket_of_a_screening(id);
    }

    public List<Ticket> find_ticket_of_a_user(Integer id) {
        return ticketRepository.find_ticket_by_userid(id);
    }

    public List<Ticket> find_ticket_of_a_cinema_admin(Integer id) {
        Cinema_Admin cinema_admin = cinema_adminService.findAdminById(id);
        List<Screen> screens = screenService.search_screen_by_cinema(cinema_admin.getCinema().getId());
        List<Screening> screenings = new ArrayList<>();
        for (Screen screen : screens) {
            screenings.addAll(screeningService.find_screenings_by_screen_id(screen.getId()));
        }
        List<Ticket> tickets = new ArrayList<>();
        List<Ticket> ftickets = new ArrayList<>();
        for (Screening screening : screenings) {
            tickets.addAll(ticketRepository.find_ticket_of_a_screening(screening.getId()));
        }
        for(Ticket ticket : tickets){
            if(ticket.getUser()!=null){
                ftickets.add(ticket);
            }
        }
        return ftickets;
    }
    public void update(Integer ticket_id,Cinema_Admin cinema_admin) {
        Ticket ticket=ticketRepository.find_ticket_by_id(ticket_id).get(0);
        ticket.setUser(cinema_admin);
    }

    public void delete_by_ticket_id(Integer id) {
        ticketRepository.delete_by_ticket_id(id);
    }

    public List<Ticket> find_ticket_within_one_week(List<Ticket> tickets) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Ticket> goaltickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Date tickettime = ticket.getCreate_time();
            Date now = new Date();
            Date tickettime1 = df.parse(df.format(tickettime));
            Date now1 = df.parse(df.format(now));
            long from = tickettime1.getTime();
            long to = now1.getTime();
            int days = (int) ((to - from) / (1000 * 60 * 60 * 24));
            if (days < 7) {
                goaltickets.add(ticket);
            }
        }
        return goaltickets;
    }

    public List<Ticket> find_ticket_within_one_month(List<Ticket> tickets) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Ticket> goaltickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Date tickettime = ticket.getCreate_time();
            Date now = new Date();
            Date tickettime1 = df.parse(df.format(tickettime));
            Date now1 = df.parse(df.format(now));
            long from = tickettime1.getTime();
            long to = now1.getTime();
            int days = (int) ((to - from) / (1000 * 60 * 60 * 24));
            if (days < 30) {
                goaltickets.add(ticket);
            }
        }
        return goaltickets;
    }

    public List<MovieSelling> display_selling_by_movie_and_ticket(List<Movie> movies, List<Ticket> tickets) {
        List<MovieSelling> movieSellings = new ArrayList<>();
        for (Movie movie : movies) {
            int num = 0;
            float price = 0;
            MovieSelling movieSelling = new MovieSelling();
            movieSelling.setCertificate(movie.getCertificate());
            movieSelling.setId(movie.getId());
            movieSelling.setShowtime(movie.getShowtime());
            movieSelling.setTitle(movie.getTitle());
            movieSelling.setType(movie.getType());
            movieSelling.setUrl(movie.getUrl());
            for (Ticket ticket : tickets) {
                if (ticket.getScreening().getMovie().getId().equals(movieSelling.getId())) {
                    num++;
                    price += ticket.getScreening().getPrice();
                }
            }
            movieSelling.setSelling_num_week(num);
            movieSelling.setMoney_week(price);
            movieSellings.add(movieSelling);
        }
        return movieSellings;
    }


    //    public Ticket generate_a_ticket(Integer screening_id,Integer seat_id) {
//    }
    public Ticket generate_a_ticket_for_app(Integer screening_id, Integer seat_id, String type, Cinema_Admin cinema_admin) {
        Ticket ticket = new Ticket();
        ticket.setType(type);
        Date date = new Date(System.currentTimeMillis());
        ticket.setCreate_time(date);
        Seat seat = seatService.find_seat_by_id(seat_id);
        Screening screening = screeningService.find_screening_by_screening_id(screening_id);
        ticket.setScreening(screening);
        ticket.setSeat(seat);
        float screeningPrice = screening.getPrice();
        if (type.equals("Adult Ticket")) {
            ticket.setPrice(screeningPrice);
        } else if (type.equals("Child Ticket")) {
            ticket.setPrice((float) (screeningPrice * 0.5));
        } else if (type.equals("Elderly Ticket")) {
            ticket.setPrice((float) (screeningPrice * 0.7));
        }
        ticket.setUser(cinema_admin);
        ticketRepository.save(ticket);
        return ticket;
    }

    public Ticket generate_a_ticket(Integer screening_id,Integer seat_id,String type) {
        Ticket ticket=new Ticket();
        ticket.setType(type);
        Date date = new Date(System.currentTimeMillis());
        ticket.setCreate_time(date);
        Seat seat=seatService.find_seat_by_id(seat_id);
        Screening screening=screeningService.find_screening_by_screening_id(screening_id);
        ticket.setScreening(screening);
        ticket.setSeat(seat);
        float screeningPrice=screening.getPrice();
        if(type.equals("Adult Ticket")) {
            ticket.setPrice(screeningPrice);
        }
        else if(type.equals("Child Ticket")) {
            ticket.setPrice((float) (screeningPrice*0.5));
        }
        else if(type.equals("Elderly Ticket")) {
            ticket.setPrice((float) (screeningPrice*0.7));
        }
        ticketRepository.save(ticket);
        return ticket;
    }

    public void delete_ticket_by_seat_id(Integer id) {
        List<Ticket> tickets = ticketRepository.find_ticket_by_seat_id(id);
        for(int i = 0; i<tickets.size();i++){
            ticketRepository.delete_ticket_by_seat_id(id);
        }
    }
}
