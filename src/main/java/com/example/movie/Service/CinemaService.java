package com.example.movie.Service;

import com.example.movie.Entity.*;
import com.example.movie.Repository.CinemaRepository;
import com.example.movie.Repository.ScreeningRepository;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CinemaService {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private Cinema_AdminService cinema_adminService;

    public List<Cinema> display_all_cinemas(){
        return cinemaRepository.display_all_cinemas();
    }

    public List<Cinema> find_cinema_by_location(String location){
        return cinemaRepository.find_cinema_by_location(location);

    }

    public List<Cinema> display_cinemas_for_a_certain_movie(Integer id) {
        List<Cinema> cinemas=new ArrayList<>();
        List<Screen> screens=new ArrayList<>();
        List<Cinema> final_cinemas=new ArrayList<>();
        List<Screening> screenings = screeningRepository.display_screenings_by_movie_id(id);
        for (int i = 0; i < screenings.size(); i++) {
            screens.add(screenings.get(i).getScreen());
            for (int j = 0; j < screens.size(); j++) {
                cinemas.add(screens.get(j).getCinema());
            }
        }
        for (int i = 0; i < cinemas.size(); ++i) {
            int a=0;
            for(int j = 0; j < final_cinemas.size(); ++j) {
                if(cinemas.get(i)==final_cinemas.get(j)) {
                    a=1;
                }
            }
            if(a==0) {
                final_cinemas.add(cinemas.get(i));
            }
        }
        return final_cinemas;
    }
    // find a cinema by id
    public Cinema getById(Integer id) {
        return cinemaRepository.display_cinema_by_id(id).get(0);
    }

    // add a cinema
    public boolean add(Integer id, String title, String location, String tel, boolean refund,
                       boolean change_time, boolean snack, boolean three_D_glasses,
                       boolean wifi, boolean rest_area, boolean children_discount
    ) {
        List<Cinema> find_cinema_by_location = cinemaRepository.find_cinema_by_location(location);
        Cinema_Admin admin = cinema_adminService.findAdminById(id);
        if (find_cinema_by_location.size() != 0) {
            return false;
        } else {
            if (title.length() > 50 || tel.length() == 20 || location.length() > 100) {
                return false;
            } else {
                Cinema cinema = new Cinema();

                cinema.setTitle(title);
                cinema.setLocation(location);
                cinema.setTel(tel);
                cinema.setRefund(refund);
                cinema.setChange_time(change_time);
                cinema.setSnack(snack);
                cinema.setThree_D_glasses(three_D_glasses);
                cinema.setWifi(wifi);
                cinema.setRest_area(rest_area);
                cinema.setChildren_discount(children_discount);

                //build a relationship between cinema and admin
                admin.setCinema(cinema);
                cinemaRepository.save(cinema);
                return true;
            }
        }
    }

    //update the information of cinema
    @Transactional
    public void update(String title, String location, String tel, boolean refund,
                       boolean change_time, boolean snack, boolean three_D_glasses,
                       boolean wifi, boolean rest_area, boolean children_discount, Integer id
    )
    {
        Cinema cinema = this.getById(id);
        cinema.setTitle(title);
        cinema.setLocation(location);
        cinema.setTel(tel);
        cinema.setRefund(refund);
        cinema.setChange_time(change_time);
        cinema.setSnack(snack);
        cinema.setThree_D_glasses(three_D_glasses);
        cinema.setWifi(wifi);
        cinema.setRest_area(rest_area);
        cinema.setChildren_discount(children_discount);
    }
}
