package com.example.movie.Service;

import com.example.movie.Entity.*;
import com.example.movie.Repository.Cinema_AdminRepository;
import com.example.movie.Repository.ScreenRepository;
import com.example.movie.Repository.ScreeningRepository;
import org.json.JSONException;
import org.json.JSONObject;
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
    @Autowired
    private AuthenticationService authenticationService;

    public List<Cinema_Admin> findByCinemaAdminUsernameAndPassword(String username,String password){
        return cinema_adminRepository.findByCinemaAdminUsernameAndPassword(username,password);

    }
    public List<Cinema_Admin> findByCinemaAdminID(Integer id){
        return cinema_adminRepository.findByCinemaAdminID(id);

    }

    public List<Cinema_Admin> findByCinemaAdminUsername(String username){
        return cinema_adminRepository.findByCinemaAdminUsername(username);

    }

    //add a cinema admin
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

    public Cinema_Admin findAdminById(Integer id) {
        return cinema_adminRepository.findByCinemaAdminId(id).get(0);
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
    public Object loginAuthentication(String username,String password) throws JSONException {
        List<Cinema_Admin> admins = cinema_adminRepository.findByCinemaAdminUsernameAndPassword(username,password);
        List<Cinema_Admin> adminExist = cinema_adminRepository.findByCinemaAdminUsername(username);
        JSONObject jsonObject = new JSONObject();
        if(adminExist.size()>0) {
            if(admins.size()>0){
                String token = authenticationService.getToken(admins.get(0));
                jsonObject.put("token", token);
                jsonObject.put("user", admins.get(0));
                //model.addAttribute("user",user);
                //return "redirect:/manage";
            }
            else{
                jsonObject.put("message", "Password is not correct");
                //return "redirect:/login";
            }
        }
        else {
            jsonObject.put("message", "Username does not exist");
            //return "redirect:/login";
        }
        return jsonObject;
    }


}
