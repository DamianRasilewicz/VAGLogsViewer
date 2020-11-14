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
    private final CsvReaderService csvReaderService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl userService, FileServiceImpl fileService, CsvReaderService csvReaderService) {
        this.userService = userService;
        this.fileService = fileService;
        this.csvReaderService = csvReaderService;
    }

    @GetMapping("/user/home")
    public String homePage(HttpSession session, Model model) {
        String loggedUserName = (String) session.getAttribute("userName");
        model.addAttribute("userName", loggedUserName);
        return "mainPage/templates/dashboard";
    }

    @PostMapping("/user/upload")
    public String postUpload(@RequestParam("file") MultipartFile file, HttpSession session) {
        String loggedUserName = (String) session.getAttribute("userName");
        User user = userService.findByUserName(loggedUserName);
        List<File> files = user.getFiles();
        String fileName = file.getOriginalFilename();
        try {
            File fileToSave = new File(fileName, LocalDate.now(), LocalTime.now(), file.getBytes());
            files.add(fileToSave);
            fileService.saveFile(fileToSave);
            user.setFiles(files);
            userService.saveUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/user/logs";
    }

    @GetMapping("/user/logs")
    public String uploadedLogs(HttpSession session, Model model) {
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        List<File> userFiles = fileService.findFilesByUserId(loggedUser.getId());
        model.addAttribute("userFiles", userFiles);
        return "mainPage/templates/logs";
    }

    @GetMapping("/user/logs/delete")
    public String deleteLog(@RequestParam Integer id) {
        fileService.deleteFileById(id);
        return "redirect:/user/logs";
    }

    @GetMapping("/user/logs/view")
    public String viewLog(@RequestParam Integer id, Model model) throws IOException {
        byte[] fileBytes = fileService.findFileById(id).getData();
        java.io.File fileFromDb = new java.io.File("fileToRead");
        FileUtils.writeByteArrayToFile(fileFromDb, fileBytes);

        List<String[]> allLines = csvReaderService.readAllDataAtOnceWithHeaders(fileFromDb);
        List<String[]> allLinesWithoutHeaders = csvReaderService.readAllDataAtOnceWithoutHeaders(fileFromDb);
        model.addAttribute("values", allLinesWithoutHeaders);

        List<String> firstHeader = csvReaderService.readFirstHeader(allLines);
        model.addAttribute("firstHeader", firstHeader);

        List<String> secondHeader = csvReaderService.readSecondHeader(allLines);
        model.addAttribute("secondHeader", secondHeader);

        List<String> thirdHeader = csvReaderService.readThirdHeader(allLines);
        model.addAttribute("thirdHeader", thirdHeader);

        return "mainPage/templates/viewLog";
    }
}

