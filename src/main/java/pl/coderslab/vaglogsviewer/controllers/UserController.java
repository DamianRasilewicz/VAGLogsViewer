package pl.coderslab.vaglogsviewer.controllers;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.vaglogsviewer.entities.File;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.CsvReaderService;
import pl.coderslab.vaglogsviewer.services.FileServiceImpl;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final FileServiceImpl fileService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl userService, FileServiceImpl fileService, CsvReaderService csvReaderService) {
        this.userService = userService;
        this.fileService = fileService;

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
        return "mainPage/dashboard";
    }

    @GetMapping("/user/profile")
    public String editProfile(HttpSession session, Model model){
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("user", loggedUser);
        return "mainPage/profile";
    }

    @PostMapping("user/profile")
    public String changedProfile(HttpSession session, Model model){
        

        return "mainPage/profile";
    }
}

