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
import pl.coderslab.vaglogsviewer.services.LogsServiceImpl;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final LogsServiceImpl fileService;
    private final CarServiceImpl carService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public AdminController(UserServiceImpl userService, LogsServiceImpl fileService, CarServiceImpl carService) {
        this.userService = userService;
        this.fileService = fileService;
        this.carService = carService;
    }

    @GetMapping("/admin/home")
    public String homePageAdmin(HttpSession session, Model model) {
        String loggedUserName = (String) session.getAttribute("userName");

        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("user", loggedUser);

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

        return "mainPage/admin/dashboard";
    }

    @GetMapping("/admin/profile")
    public String editProfileAdmin(HttpSession session, Model model){
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("loggedUser", loggedUser);
        logger.error(loggedUser.toString());
        return "mainPage/admin/profileEdit";
    }

    @PostMapping("admin/profile")
    public String changedProfileAdmin(HttpSession session, User loggedUser){
        userService.updateUser(loggedUser.getName(), loggedUser.getEmail(), loggedUser.getFirstName(),
                loggedUser.getLastName(), loggedUser.getPassword(), loggedUser.getId());
        session.removeAttribute("userName");
        session.setAttribute("userName", loggedUser.getName());
        logger.error(loggedUser.toString());
        return "redirect:/admin/profile/success";

    }
    @GetMapping("admin/profile/success")
    public String profileChangedSuccessfullyAdmin(HttpSession session, Model model){
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("loggedUser", loggedUser);
        logger.error(loggedUser.toString());
        return "mainPage/admin/profileEditSuccess";
    }

    @GetMapping("admin/users")
    public String usersAdmin(Model model){
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "mainPage/admin/users";
    }

}