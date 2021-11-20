package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.Picture;

import java.util.List;

public interface PictureService {

    Picture findByPictureId(Integer id);

    void savePicture(Picture picture);

    void deletePictureById(Integer id);

    Picture findPictureByUserId(Integer userId);

    void deleteLastPicture();

    void deletePictureByUserID(Integer userId);
}
