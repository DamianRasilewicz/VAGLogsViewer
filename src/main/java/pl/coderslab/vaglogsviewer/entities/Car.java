package pl.coderslab.vaglogsviewer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String brand;
    private String model;
    private String modelType;
    private String productionYear;
    private String mileage;
    private String engineType;
    private String engineCapacity;
    private String engineCode;

    @ManyToOne
    private User user;


    public Car(){
    }

    public Car(String brand, String model, String modelType, String productionYear, String mileage, String engineType, String engineCapacity, String engineCode) {
        this.brand = brand;
        this.model = model;
        this.modelType = modelType;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
        this.engineCode = engineCode;
    }
}
