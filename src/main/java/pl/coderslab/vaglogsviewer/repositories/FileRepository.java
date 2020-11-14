package pl.coderslab.vaglogsviewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.vaglogsviewer.entities.File;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    File findFileByFileName(String fileName);

    File findFileById(Integer id);

    @Query(value =  "SELECT * FROM files WHERE user_id = ?1", nativeQuery = true)
    List<File> findFilesByUserId(Long userId);

    @Query(value =  "SELECT * FROM files WHERE user_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    File findLastFileByUserId(Long userId);
}
