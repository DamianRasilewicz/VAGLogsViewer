package pl.coderslab.vaglogsviewer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.vaglogsviewer.entities.Car;
import pl.coderslab.vaglogsviewer.entities.File;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.CarServiceImpl;
import pl.coderslab.vaglogsviewer.services.FileServiceImpl;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final FileServiceImpl fileService;
    private final CarServiceImpl carService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl userService, FileServiceImpl fileService, CarServiceImpl carService) {
        this.userService = userService;
        this.fileService = fileService;
        this.carService = carService;

    }

    @GetMapping("/user/home")
    public String homePage(HttpSession session, Model model) {
        String loggedUserName = (String) session.getAttribute("userName");
        model.addAttribute("userName", loggedUserName);

        User loggedUser = userService.findByUserName(loggedUserName);

        File userLastFile = fileService.findLastFileByUserId(loggedUser.getId());
        model.addAttribute("userLastFile", userLastFile);

        List<File> userFiles = fileService.findFilesByUserId(loggedUser.getId());
        int numberOfUserFiles = userFiles.size();
        model.addAttribute("numberOfUserFiles", numberOfUserFiles);
        logger.error(String.valueOf(numberOfUserFiles));

        List<Car> userCars = carService.findCarsByUserId(loggedUser.getId());
        model.addAttribute("userCarList", userCars);
        int numberOfUserCar = userCars.size();
        model.addAttribute("numberOfUserCars", numberOfUserCar);
        logger.error(String.valueOf(numberOfUserCar));

        return "mainPage/dashboard";
    }

    @GetMapping("/user/profile")
    public String editProfile(HttpSession session, Model model){
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("loggedUser", loggedUser);
        logger.error(loggedUser.toString());
        return "mainPage/profileEdit";
    }

    @PostMapping("user/profile")
    public String changedProfile(HttpSession session, User loggedUser){
        userService.updateUser(loggedUser.getName(), loggedUser.getEmail(), loggedUser.getFirstName(),
                loggedUser.getLastName(), loggedUser.getPassword(), loggedUser.getId());
        session.removeAttribute("userName");
        session.setAttribute("userName", loggedUser.getName());
        logger.error(loggedUser.toString());
        return "redirect:/user/profile/success";

    }
    @GetMapping("user/profile/success")
    public String profileChangedSuccessfully(HttpSession session, Model model){
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("loggedUser", loggedUser);
        logger.error(loggedUser.toString());
        return "mainPage/profileEditSuccess";
    }


}

