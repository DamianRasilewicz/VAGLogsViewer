package pl.coderslab.vaglogsviewer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;
import pl.coderslab.vaglogsviewer.services.VLVUserDetails;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;



@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    

    @GetMapping("/login")

    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "landingPage/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, BindingResult result,HttpSession session) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        User loggedInUser = ((VLVUserDetails) authentication.getPrincipal()).getUserDetails();
        logger.error(user.getName() + ' ' + user.getPassword());

        if (result.hasErrors()) {
            return "redirect:/login?error";
        }

        session.setAttribute("userName", loggedInUser.getName());
        if (loggedInUser.getRole().getName().equals("USER")){
            return "redirect:/user/home";
        }else{
            return "redirect:/admin/home";
        }
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof VLVUserDetails)) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
    }

    @GetMapping("/logout")
    public String logout(SessionStatus session, HttpSession httpSession) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        httpSession.removeAttribute("userName");
        return "redirect:/";
    }

}
