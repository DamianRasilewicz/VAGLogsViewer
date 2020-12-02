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
import pl.coderslab.vaglogsviewer.entities.Car;
import pl.coderslab.vaglogsviewer.entities.File;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.CarServiceImpl;
import pl.coderslab.vaglogsviewer.services.CsvReaderService;
import pl.coderslab.vaglogsviewer.services.LogsServiceImpl;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class LogsController {

    private final UserServiceImpl userService;
    private final LogsServiceImpl fileService;
    private final CsvReaderService csvReaderService;
    private final CarServiceImpl carService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public LogsController(UserServiceImpl userService, LogsServiceImpl fileService, CsvReaderService csvReaderService, CarServiceImpl carService) {
        this.userService = userService;
        this.fileService = fileService;
        this.csvReaderService = csvReaderService;
        this.carService = carService;
    }

    @PostMapping("/user/upload")
    public String postUpload(@RequestParam("file") MultipartFile file, HttpSession session, @RequestParam("car") Long carId) {
        String loggedUserName = (String) session.getAttribute("userName");
        User user = userService.findByUserName(loggedUserName);

        List<File> files = user.getFiles();
        String fileName = file.getOriginalFilename();

        Car selectedCar = carService.findByCarId(carId);
        logger.error(String.valueOf(selectedCar));

        try {
            File fileToSave = new File(fileName, LocalDate.now(), LocalTime.now(), selectedCar, file.getBytes());
            fileToSave.setUser(user);
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
        int numberOfUserFiles = userFiles.size();
        model.addAttribute("numberOfUserFiles", numberOfUserFiles);
        logger.warn(String.valueOf(loggedUser.getFiles()));
        return "mainPage/user/logs";
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

        return "mainPage/user/viewLog";
    }

    @PostMapping("/admin/upload")
    public String postUploadAdmin(@RequestParam("file") MultipartFile file, HttpSession session, @RequestParam("car") Long carId) {
        String loggedUserName = (String) session.getAttribute("userName");
        User user = userService.findByUserName(loggedUserName);

        List<File> files = user.getFiles();
        String fileName = file.getOriginalFilename();

        Car selectedCar = carService.findByCarId(carId);
        logger.error(String.valueOf(selectedCar));

        try {
            File fileToSave = new File(fileName, LocalDate.now(), LocalTime.now(), selectedCar, file.getBytes());
            fileToSave.setUser(user);
            files.add(fileToSave);
            fileService.saveFile(fileToSave);
            user.setFiles(files);
            userService.saveUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/logs";
    }

    @GetMapping("/admin/logs")
    public String uploadedLogsAdmin(HttpSession session, Model model) {
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        List<File> userFiles = fileService.findFilesByUserId(loggedUser.getId());
        model.addAttribute("userFiles", userFiles);
        int numberOfUserFiles = userFiles.size();
        model.addAttribute("numberOfUserFiles", numberOfUserFiles);
        logger.warn(String.valueOf(loggedUser.getFiles()));
        return "mainPage/admin/logs";
    }

    @GetMapping("/admin/logs/delete")
    public String deleteLogAdmin(@RequestParam Integer id) {
        fileService.deleteFileById(id);
        return "redirect:/admin/logs";
    }

    @GetMapping("/admin/logs/view")
    public String viewLogAdmin(@RequestParam Integer id, Model model) throws IOException {
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

        return "mainPage/admin/viewLog";
    }
}
