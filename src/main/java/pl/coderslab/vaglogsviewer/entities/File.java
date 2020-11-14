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

    @Lob
    private byte[] data;
//
//    @ManyToOne
//    private User user;


    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", uploadedDate=" + uploadedDate +
                ", uploadedTime=" + uploadedTime +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public File(){}

    public File(String fileName, LocalDate uploadedDate, LocalTime uploadedTime, byte[] data) {
        this.fileName = fileName;
        this.uploadedDate = uploadedDate;
        this.uploadedTime = uploadedTime;
        this.data = data;
    }
}
