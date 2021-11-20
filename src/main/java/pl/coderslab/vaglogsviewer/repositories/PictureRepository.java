package pl.coderslab.vaglogsviewer.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.vaglogsviewer.entities.Picture;
import javax.transaction.Transactional;

@Repository
@EntityScan(basePackages = "pl.coderslab.vaglogsviewer.entities")
public interface PictureRepository extends JpaRepository<Picture, Integer> {

    Picture findPictureByPictureName(String pictureName);

    Picture findPictureById(Integer id);

    @Query(value =  "SELECT * FROM vag_logs_viewer.pictures WHERE user_id = ?1", nativeQuery = true)
    Picture findPictureByUserId(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM vag_logs_viewer.pictures WHERE user_id = ?1", nativeQuery = true)
    void deletePictureByUserID(Integer userId);
}
