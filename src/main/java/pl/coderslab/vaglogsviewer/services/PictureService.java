package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.Picture;

import java.util.List;

public interface PictureService {

    Picture findByPictureId(Long id);

    void savePicture(Picture picture);

    void deletePictureById(Long id);

    List<Picture> findPicturesByUserId(Long userId);
}
