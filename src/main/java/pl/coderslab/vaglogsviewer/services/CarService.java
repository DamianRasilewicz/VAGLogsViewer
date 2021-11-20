package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.Car;
import java.util.List;

public interface CarService {

    Car findByCarId(Integer id);

    void saveCar(Car car);

    void deleteFileById(Integer id);

    List<Car> findCarsByUserId(Integer userId);
}

