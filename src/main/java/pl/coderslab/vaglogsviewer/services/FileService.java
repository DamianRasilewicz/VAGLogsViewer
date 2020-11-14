package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.File;

import java.util.List;


public interface FileService {

    File findByFileName(String fileName);

    File findFileById(Integer id);

    void saveFile(File file);

    void deleteFileById(Integer id);

    List<File> findFilesByUserId(Long userId);

    File findLastFileByUserId(Long userId);
}
