package pl.coderslab.vaglogsviewer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.vaglogsviewer.entities.*;
import pl.coderslab.vaglogsviewer.services.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final LogsServiceImpl fileService;
    private final CarServiceImpl carService;
    private final RoleServiceImpl roleService;
    private final PictureServiceImpl pictureService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public AdminController(UserServiceImpl userService, LogsServiceImpl fileService, CarServiceImpl carService, RoleServiceImpl roleService, PictureServiceImpl pictureService) {
        this.userService = userService;
        this.fileService = fileService;
        this.carService = carService;
        this.roleService = roleService;
        this.pictureService = pictureService;
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

        return "mainPage/admin/dashboard";
    }

    @GetMapping("/admin/profile")
    public String editProfileAdmin(HttpSession session, Model model){
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


        return "mainPage/admin/profileEdit";
    }

    @PostMapping("admin/profile")
    public String changedProfileAdmin(HttpSession session, User loggedUser){
        String hashedPassword = BCrypt.hashpw(loggedUser.getPassword(), BCrypt.gensalt());
        userService.updateProfile(loggedUser.getName(), loggedUser.getEmail(), loggedUser.getFirstName(),
                loggedUser.getLastName(), hashedPassword, loggedUser.getId());
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

    @GetMapping("admin/users/edit")
    public String usersEditAdmin(@RequestParam Long id, Model model) {
        User editingUser = userService.findUserById(id);
        model.addAttribute("editingUser", editingUser);
        return "mainPage/admin/userEdit";
    }

    @PostMapping("admin/users/edit")
    public String usersEditPostAdmin(@RequestParam String roleName, User editingUser) {
        Role userRole = roleService.findByRoleName(roleName);
        userService.updateUser(editingUser.getName(),editingUser.getEmail(), editingUser.getPassword(), editingUser.getEnabled(),
                               editingUser.getFirstName(), editingUser.getLastName(), editingUser.getId());
        roleService.updateUserRole(userRole.getId(), editingUser.getId());
        return "redirect:/admin/users";
    }

    @PostMapping("admin/profile/picture")
    public String changeAdminPicture (@RequestParam("picture") MultipartFile file, HttpSession session){
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

        return "redirect:/admin/profile";
    }

    @GetMapping("admin/users/delete")
    public String usersDeleteAdmin(@RequestParam Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

}
