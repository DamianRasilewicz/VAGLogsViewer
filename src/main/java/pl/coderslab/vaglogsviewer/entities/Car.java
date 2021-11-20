package pl.coderslab.vaglogsviewer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    @Size(min = 4, message = "Brand is too short (minimum is 4 characters)")
    private String brand;

    @Size(min = 1, message = "Model is too short (minimum is 1 characters)")
    private String model;

    @Size(min = 2, message = "Model Type is too short (minimum is 2 characters)")
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
