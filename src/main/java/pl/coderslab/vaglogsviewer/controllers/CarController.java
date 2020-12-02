package pl.coderslab.vaglogsviewer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.vaglogsviewer.entities.Car;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.CarServiceImpl;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;

import javax.servlet.http.HttpSession;

@Controller
public class CarController {

    public final UserServiceImpl userService;
    public final CarServiceImpl carService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public CarController(UserServiceImpl userService, CarServiceImpl carService) {
        this.userService = userService;
        this.carService = carService;
    }

    @GetMapping("user/cars/add")
    public String addUserCar(HttpSession session, Model model) {
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("loggedUser", loggedUser);

        Car carToAdd = new Car();
        model.addAttribute("carToAdd", carToAdd);
        return "mainPage/user/carAdd";
    }

    @PostMapping("user/cars/add")
    public String addedUserCar(HttpSession session,Car carToAdd) {
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);

        carToAdd.setUser(loggedUser);
        carService.saveCar(carToAdd);
        return "redirect:/user/home";
    }

    @GetMapping("/user/cars/edit")
    public String editCar(@RequestParam Long id, Model model){
        Car editingCar = carService.findByCarId(id);
        model.addAttribute("editingCar", editingCar);
        return "mainPage/user/carEdit";
    }

    @PostMapping("user/cars/edit")
    public String changedCar(HttpSession session, Car editingCar){
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        editingCar.setUser(loggedUser);
        carService.saveCar(editingCar);
        session.setAttribute("editedCar", editingCar);
        return "redirect:/user/cars/edit/success";

    }
    @GetMapping("/user/cars/edit/success")
    public String carChangedSuccessfully(HttpSession session, Model model){
        Car editedCar = (Car) session.getAttribute("editedCar");
        model.addAttribute("editedCar", editedCar);
        session.removeAttribute("editedCar");
        return "mainPage/user/carEditSuccess";
    }

    @GetMapping("admin/cars/add")
    public String addUserCarAdmin(HttpSession session, Model model) {
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        model.addAttribute("loggedUser", loggedUser);

        Car carToAdd = new Car();
        model.addAttribute("carToAdd", carToAdd);
        return "mainPage/admin/carAdd";
    }

    @PostMapping("admin/cars/add")
    public String addedUserCarAdmin(HttpSession session,Car carToAdd) {
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);

        carToAdd.setUser(loggedUser);
        carService.saveCar(carToAdd);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/cars/edit")
    public String editCarAdmin(@RequestParam Long id, Model model){
        Car editingCar = carService.findByCarId(id);
        model.addAttribute("editingCar", editingCar);
        return "mainPage/admin/carEdit";
    }

    @PostMapping("user/admin/edit")
    public String changedCarAdmin(HttpSession session, Car editingCar){
        String loggedUserName = (String) session.getAttribute("userName");
        User loggedUser = userService.findByUserName(loggedUserName);
        editingCar.setUser(loggedUser);
        carService.saveCar(editingCar);
        session.setAttribute("editedCar", editingCar);
        return "redirect:/admin/cars/edit/success";

    }
    @GetMapping("/admin/cars/edit/success")
    public String carChangedSuccessfullyAdmin(HttpSession session, Model model){
        Car editedCar = (Car) session.getAttribute("editedCar");
        model.addAttribute("editedCar", editedCar);
        session.removeAttribute("editedCar");
        return "mainPage/admin/carEditSuccess";
    }
}
