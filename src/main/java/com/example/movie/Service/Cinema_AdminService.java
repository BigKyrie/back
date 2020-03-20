package com.example.movie.Service;

import com.example.movie.Entity.*;
import com.example.movie.Repository.Cinema_AdminRepository;
import com.example.movie.Repository.ScreenRepository;
import com.example.movie.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class Cinema_AdminService {
    @Autowired
    private Cinema_AdminRepository cinema_adminRepository;
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    public List<Cinema_Admin> findByCinemaAdminUsernameAndPassword(String username,String password){
        return cinema_adminRepository.findByCinemaAdminUsernameAndPassword(username,password);

    }

    public boolean add(String username, String password) {
        List<Cinema_Admin> findCinemaAdminUserName = cinema_adminRepository.findByCinemaAdminUsername(username);
        if (findCinemaAdminUserName.size() != 0) {
            return false;
        } else {
            if (username.length() > 20 || username.length() == 0 || password.length() > 20 || password.length() == 0) {
                return false;
            } else {
                Cinema_Admin cinema_admin = new Cinema_Admin();
                cinema_admin.setUsername(username);
                cinema_admin.setPassword(password);
                cinema_adminRepository.save(cinema_admin);
                return true;
            }
        }
    }

    public List<Movie> findMovieByCinema(Integer cinema_id) {
        List<Screen> screens=new ArrayList<>();
        List<Screening> screenings=new ArrayList<>();
        List<Movie> movies=new ArrayList<>();
        List<Movie> final_movies=new ArrayList<>();
        screens=screenRepository.search_screen_by_cinema(cinema_id);
        for(int i=0;i<screens.size();i++) {
            screenings=screeningRepository.find_screenings_by_screen_id(screens.get(i).getId());
            for(int j=0;j<screenings.size();j++) {
                movies.add(screenings.get(j).getMovie());
            }

        }
        for (int i = 0; i < movies.size(); i++) {
            int a=0;
            for(int j = 0; j < final_movies.size(); ++j) {
                if(movies.get(i)==final_movies.get(j)) {
                    a=1;
                }
            }
            if(a==0) {
                final_movies.add(movies.get(i));
            }
        }
        return final_movies;
    }

}
