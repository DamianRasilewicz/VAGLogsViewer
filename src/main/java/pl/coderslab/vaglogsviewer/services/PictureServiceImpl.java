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
    public Picture findByPictureId(Long id) {
        return pictureRepository.findPictureById(id);
    }

    @Override
    public void savePicture(Picture picture) {
        pictureRepository.save(picture);
    }

    @Override
    public void deletePictureById(Long id) {
        pictureRepository.deleteById(id);
    }

    @Override
    public Picture findPictureByUserId(Long userId) {
       return pictureRepository.findPictureByUserId(userId);
    }

    @Override
    public void deleteLastPicture() {
        pictureRepository.deleteAll();
    }

    @Override
    public void deletePictureByUserID(Long userId) {
        pictureRepository.deletePictureByUserID(userId);
    }
}
