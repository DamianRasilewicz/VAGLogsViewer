package pl.coderslab.vaglogsviewer.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;


@Data
@Entity
@Getter
@Setter
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String fileName;

    @Column(name = "uploadedDate")
    private LocalDate uploadedDate;

    @Column(name = "uploadedTime")
    private LocalTime uploadedTime;

    @OneToOne()
    @JoinColumn(name = "cars_id", unique = true)
    private Car car;

    @Lob
    private byte[] data;

    public File(){}

    public File(String fileName, LocalDate uploadedDate, LocalTime uploadedTime,Car car, byte[] data) {
        this.fileName = fileName;
        this.uploadedDate = uploadedDate;
        this.uploadedTime = uploadedTime;
        this.car = car;
        this.data = data;
    }
}
