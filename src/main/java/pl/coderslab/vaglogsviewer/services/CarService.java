package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.Car;
import java.util.List;

public interface CarService {

    Car findByCarId(Long id);

    void saveCar(Car car);

    void deleteFileById(Long id);

    List<Car> findCarsByUserId(Long userId);
}

