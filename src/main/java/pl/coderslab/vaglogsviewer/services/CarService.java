package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.Car;


public interface CarService {

    Car findByCarId(Long id);

    void saveCar(Car car);
}

