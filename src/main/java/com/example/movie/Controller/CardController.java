package com.example.movie.Controller;
import com.example.movie.Entity.*;
import com.example.movie.Service.*;
import com.example.movie.Service.Cinema_AdminService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/card")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private TicketService ticketService;

    @PostMapping(path = "/bindCard")
    public boolean bind_card(@RequestParam String card_number,String password) {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Cinema_Admin cinema_admin=cinema_adminService.findAdminById(userInfo.getUserId());
        if(cardService.find_card_by_number(card_number).size()!=0 || cardService.find_card_by_user_id(userInfo.getUserId()).size()!=0) {
            return false;
        }
        else {
            cardService.add(card_number,password,cinema_admin);
            return true;
        }

    }
    @PostMapping(path = "/verifyPassword/{ticket_id}")
    public boolean verify_card_password(@RequestParam String password,@PathVariable Integer ticket_id) {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Cinema_Admin cinema_admin=cinema_adminService.findAdminById(userInfo.getUserId());
        if(cardService.find_card_by_user_id(userInfo.getUserId()).size()==0) {
            return false;
        }
        else {
            Card card=cardService.find_card_by_user_id(userInfo.getUserId()).get(0);
            if(card.getPassword().equals(password)) {
                Ticket ticket=ticketService.find_ticket_by_id(ticket_id);
                ticket.setUser(cinema_admin);
                return true;
            }
            else {
                return false;
            }
        }

    }

    @GetMapping(path = "/cashPay/{ticket_id}")
    public boolean cash_pay(@PathVariable Integer ticket_id) {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Cinema_Admin cinema_admin=cinema_adminService.findAdminById(userInfo.getUserId());
        Ticket ticket=ticketService.find_ticket_by_id(ticket_id);
        ticket.setUser(cinema_admin);
        return true;
    }


    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
