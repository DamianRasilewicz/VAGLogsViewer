package pl.coderslab.vaglogsviewer.controllers;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.vaglogsviewer.entities.Car;
import pl.coderslab.vaglogsviewer.entities.File;
import pl.coderslab.vaglogsviewer.entities.Picture;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.CarServiceImpl;
import pl.coderslab.vaglogsviewer.services.LogsServiceImpl;
import pl.coderslab.vaglogsviewer.services.PictureServiceImpl;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final LogsServiceImpl fileService;
    private final CarServiceImpl carService;
    private final PictureServiceImpl pictureService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl userService, LogsServiceImpl fileService, CarServiceImpl carService, PictureServiceImpl pictureService) {
        this.userService = userService;
        this.fileService = fileService;
        this.carService = carService;
        this.pictureService = pictureService;

    }

    @GetMapping("/user/home")
    public String homePage(HttpSession session, Model model) {
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

        Picture userPicture = pictureService.findPictureByUserId(loggedUser.getId());

        if (userPicture == null){
            byte[] pictureBytes = pictureService.findByPictureId(1L).getData();
            String picture = "";
            picture = Base64.getEncoder().encodeToString(pictureBytes);
            model.addAttribute("userPicture", picture);
        }else {
            byte[] pictureBytes = userPicture.getData();;
            String picture = "";
            picture = Base64.getEncoder().encodeToString(pictureBytes);
            model.addAttribute("userPicture", picture);
        }

        return "mainPage/user/dashboard";
    }

    @GetMapping("/user/profile")
    public String editProfile(HttpSession session, Model model) throws IOException{
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("loggedUser", loggedUser);
        logger.error(loggedUser.toString());


        Picture userPicture = pictureService.findPictureByUserId(loggedUser.getId());

        if (userPicture == null){
            byte[] pictureBytes = pictureService.findByPictureId(1L).getData();
            String picture = "";
            picture = Base64.getEncoder().encodeToString(pictureBytes);
            model.addAttribute("userPicture", picture);
        }else {
            byte[] pictureBytes = userPicture.getData();;
            String picture = "";
            picture = Base64.getEncoder().encodeToString(pictureBytes);
            model.addAttribute("userPicture", picture);
        }

        return "mainPage/user/profileEdit";
    }

    @PostMapping("user/profile")
    public String changedProfile(HttpSession session, User loggedUser){
        String hashedPassword = BCrypt.hashpw(loggedUser.getPassword(), BCrypt.gensalt());
        userService.updateProfile(loggedUser.getName(), loggedUser.getEmail(), loggedUser.getFirstName(),
                loggedUser.getLastName(), hashedPassword , loggedUser.getId());
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
        return "mainPage/user/profileEditSuccess";
    }

    @PostMapping("user/profile/picture")
    public String changeUserPicture (@RequestParam("picture") MultipartFile file, HttpSession session){
        String loggedUserName = (String) session.getAttribute("userName");
        User user = userService.findByUserName(loggedUserName);

        try {
            Picture pictureToSave = new Picture(file.getOriginalFilename(), user, file.getBytes());
            pictureToSave.setUser(user);
            pictureService.deletePictureByUserID(user.getId());
            pictureService.savePicture(pictureToSave);
            user.setPicture(pictureToSave);
            userService.saveUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/user/profile";
    }

}

