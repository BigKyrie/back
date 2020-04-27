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
    public @ResponseBody boolean bind_card(@RequestParam String card_number,String password,String user_id) {
        Cinema_Admin cinema_admin=cinema_adminService.findAdminById(Integer.parseInt(user_id));
        if(cardService.find_card_by_number(card_number).size()!=0 || cardService.find_card_by_user_id(Integer.parseInt(user_id)).size()!=0) {
            return false;
        }
        else {
            cardService.add(card_number,password,cinema_admin);
            return true;
        }

    }
    @GetMapping(path = "/toEditCard")
    public String to_edit_card(Model model) {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Card card=cardService.find_card_by_user_id(userInfo.getUserId()).get(0);
        model.addAttribute("card",card);
        return "card_edit";
    }
    @PostMapping(path = "/editCard")
    public String edit_card(@RequestParam String card_number,String password,String verify_password) {
        if(!password.equals(verify_password)) {

        }
        else {
            HttpSession session = getRequest().getSession();
            UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
            Card card=cardService.find_card_by_user_id(userInfo.getUserId()).get(0);
            cardService.update(card_number,password,card);

        }
        return "redirect:/demo/mine";
    }
    @PostMapping(path = "/bindCardForWeb")
    public String bind_card_for_web(@RequestParam String card_number,String password,String verify_password) {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Cinema_Admin cinema_admin=cinema_adminService.findAdminById(userInfo.getUserId());
        if(cardService.find_card_by_number(card_number).size()!=0 || cardService.find_card_by_user_id(userInfo.getUserId()).size()!=0) {
            return "redirect:/card/toBindCard";
        }
        else {
            if(!password.equals(verify_password)) {
                return "redirect:/card/toBindCard";
            }
            else {
                cardService.add(card_number,password,cinema_admin);
                return "redirect:/demo/mine";
            }

        }
    }

    @PostMapping(path = "/cardPay/{screening_id}/{seat_id}")
    public @ResponseBody boolean verify_card_password_for_app(@RequestParam String password,String type, String user_id,
                                                @PathVariable Integer screening_id,@PathVariable Integer seat_id) {
        Ticket ticket=ticketService.generate_a_ticket(screening_id,seat_id,type);
        //HttpSession session = getRequest().getSession();
        //UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Cinema_Admin cinema_admin=cinema_adminService.findAdminById(Integer.parseInt(user_id));
        if(cardService.find_card_by_user_id(Integer.parseInt(user_id)).size()==0) {
            return false;
        }
        else {
            Card card=cardService.find_card_by_user_id(Integer.parseInt(user_id)).get(0);
            if(card.getPassword().equals(password)) {
                ticket.setUser(cinema_admin);
                return true;
            }
            else {
                return false;
            }
        }
    }
    @GetMapping(path = "/cashPay/{screening_id}/{seat_id}")
    public @ResponseBody boolean cash_pay_for_app(@RequestParam String type, String user_id,
                                    @PathVariable Integer screening_id,@PathVariable Integer seat_id) {
        Ticket ticket=ticketService.generate_a_ticket(screening_id,seat_id,type);
        //HttpSession session = getRequest().getSession();
        //UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Cinema_Admin cinema_admin=cinema_adminService.findAdminById(Integer.parseInt(user_id));
        ticket.setUser(cinema_admin);
        return true;
    }

    @GetMapping(path = "/toBindCard")
    public String to_bind_card() {
        return "bind_card";
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
