package pl.coderslab.vaglogsviewer.controllers;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.vaglogsviewer.entities.File;
import pl.coderslab.vaglogsviewer.services.CsvReaderService;
import pl.coderslab.vaglogsviewer.services.LogsServiceImpl;

import java.io.IOException;
import java.util.List;

@Controller
public class UsersLogsController {

    private final LogsServiceImpl fileService;
    private final CsvReaderService csvReaderService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UsersLogsController(LogsServiceImpl fileService, CsvReaderService csvReaderService) {
        this.fileService = fileService;
        this.csvReaderService = csvReaderService;
    }

    @GetMapping("/user/usersLogs")
    public String usersLogs(Model model) {
        List<File> allFiles = fileService.findAllFiles();
        model.addAttribute("allFiles", allFiles);
        return "mainPage/user/usersLogs";
    }

    @GetMapping("/user/usersLogs/view")
    public String usersLogsView(@RequestParam Integer id, Model model) throws IOException {
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
    @GetMapping("/admin/usersLogs/delete")
    public String deleteUserLog(@RequestParam Integer id) {
        fileService.deleteFileById(id);
        return "redirect:/admin/usersLogs";
    }

    @GetMapping("/admin/usersLogs")
    public String usersLogsAdmin(Model model) {
        List<File> allFiles = fileService.findAllFiles();
        model.addAttribute("allFiles", allFiles);
        return "mainPage/admin/usersLogs";
    }

    @GetMapping("/admin/usersLogs/view")
    public String usersLogsViewAdmin(@RequestParam Integer id, Model model) throws IOException {
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