package com.example.movie.Service;

import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Screen;
import com.example.movie.Repository.CinemaRepository;
import com.example.movie.Repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ScreenService {
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private Cinema_AdminService cinema_adminService;

    public List<Screen> display_all_screens(){
        return screenRepository.display__all_screens();
    }

    public List<Screen> get_screen_by_id(Integer id){ return screenRepository.search_screen_by_id(id);}

    public List<Screen> search_screen_by_cinema(Integer id){
        return screenRepository.search_screen_by_cinema(id);
    }

    public List<Screen> get_screen_by_cinema_id(Integer id) {return screenRepository.search_screen_by_cinems_id(id); }

    public boolean add(Integer admin_id, Integer num) {
        Cinema_Admin admin = cinema_adminService.findAdminById(admin_id);
        Screen screen = new Screen();
        screen.setCinema(admin.getCinema());
        screen.setNum(num);
        screenRepository.save(screen);
        return true;
    }
    public Screen find_screen_by_num_and_cinema(Integer num,Integer cinema_id) {
        return screenRepository.find_screen_by_num_and_cinema(num,cinema_id).get(0);
    }


}
