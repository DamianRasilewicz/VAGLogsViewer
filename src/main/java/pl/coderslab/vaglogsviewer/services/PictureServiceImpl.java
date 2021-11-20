package pl.coderslab.vaglogsviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.vaglogsviewer.entities.Picture;
import pl.coderslab.vaglogsviewer.repositories.PictureRepository;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService{

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture findByPictureId(Integer id) {
        return pictureRepository.findPictureById(id);
    }

    @Override
    public void savePicture(Picture picture) {
        pictureRepository.save(picture);
    }

    @Override
    public void deletePictureById(Integer id) {
        pictureRepository.deleteById(id);
    }

    @Override
    public Picture findPictureByUserId(Integer userId) {
       return pictureRepository.findPictureByUserId(userId);
    }

    @Override
    public void deleteLastPicture() {
        pictureRepository.deleteAll();
    }

    @Override
    public void deletePictureByUserID(Integer userId) {
        pictureRepository.deletePictureByUserID(userId);
    }
}
