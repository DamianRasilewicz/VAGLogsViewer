package pl.coderslab.vaglogsviewer.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String pictureName;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;

    public Picture(){}

    public Picture(String pictureName, User user, byte[] data) {
        this.pictureName = pictureName;
        this.user = user;
        this.data = data;
    }
}
