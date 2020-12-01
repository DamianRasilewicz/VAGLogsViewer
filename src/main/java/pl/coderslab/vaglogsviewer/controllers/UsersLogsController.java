package pl.coderslab.vaglogsviewer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.vaglogsviewer.entities.File;
import pl.coderslab.vaglogsviewer.services.FileServiceImpl;
import java.util.List;

@Controller
public class UsersLogsController {

    private final FileServiceImpl fileService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UsersLogsController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/user/usersLogs")
    public String usersLogs(Model model) {
    List<File> allFiles = fileService.findAllFiles();
    model.addAttribute("allFiles", allFiles);
    return "mainPage/usersLogs";
    }
}
