package com.example.movie.Controller;

import com.example.movie.Entity.Comment;
import com.example.movie.Entity.Movie;
import com.example.movie.Service.CommentService;
import com.example.movie.Service.MovieService;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/comment")
public class CommentController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private CommentService commentService;

    @GetMapping(path="/mine")
    public String my_comment(Model model){
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Comment> comments = commentService.findbyUserid(userInfo.getUserId());
        model.addAttribute("comments",comments);
        return "comment_mine";
    }

    @RequestMapping(path="/delete/{comment_id}")
    public String delete_comment(@PathVariable(name="comment_id") Integer comment_id){
        commentService.delete_by_comment_id(comment_id);
        return "redirect:/comment/mine";
    }

    @GetMapping(path="/make_comment/{movie_id}")
    public String makecomment(Model model, @PathVariable(name="movie_id") Integer movie_id){
        Movie movie=movieService.findMoviebyID(movie_id);
        model.addAttribute("movie",movie);
        return "comment_form";
    }

    @GetMapping(path="/edit_comment/{movie_id}")
    public String editcomment(Model model, @PathVariable(name="movie_id") Integer movie_id){
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Comment> comments = commentService.find_by_movie_and_user(movie_id,userInfo.getUserId());
        if(comments.size()>0){
            Comment comment = comments.get(0);
            model.addAttribute("comment",comment);
            return "comment_edit";
        }
        else{
            return "redirect:/demo/myMovie";
        }
    }

    @PostMapping(path="/add/{movie_id}")
    public String add(@RequestParam String comment, Integer grade, @PathVariable(name="movie_id") Integer movie_id){
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        commentService.add(comment,grade,movie_id,userInfo.getUserId());
        return "redirect:/demo/myMovie";

    }

    @PostMapping(path="/edit/{comment_id}")
    public String edit(@RequestParam String comment, Integer grade, @PathVariable(name="comment_id") Integer comment_id){
        commentService.update(comment,grade,comment_id);
        return "redirect:/demo/myMovie";

    }

    @GetMapping(path="/view_comment/{movie_id}")
    public String viewcomment(Model model, @PathVariable(name="movie_id") Integer movie_id){
        List<Comment> comments = commentService.find_by_movie_id(movie_id);
        model.addAttribute("comments",comments);
        return "view_comment";

    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }



}
