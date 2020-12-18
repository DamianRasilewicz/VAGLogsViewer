package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.Picture;

import java.util.List;

public interface PictureService {

    Picture findByPictureId(Long id);

    void savePicture(Picture picture);

    void deletePictureById(Long id);

    Picture findPictureByUserId(Long userId);

    void deleteLastPicture();

    void deletePictureByUserID(Long userId);
}
