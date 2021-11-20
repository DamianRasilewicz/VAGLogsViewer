package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.File;

import java.util.List;


public interface LogsService {

    File findByFileName(String fileName);

    File findFileById(Integer id);

    void saveFile(File file);

    void deleteFileById(Integer id);

    List<File> findFilesByUserId(Integer userId);

    File findLastFileByUserId(Integer userId);

    List<File> findAllFiles();
}
