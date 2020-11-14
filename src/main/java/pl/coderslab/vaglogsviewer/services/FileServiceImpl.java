package pl.coderslab.vaglogsviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.vaglogsviewer.entities.File;
import pl.coderslab.vaglogsviewer.repositories.FileRepository;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {


    private final FileRepository fileRepository;
    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File findByFileName(String fileName) {
        return fileRepository.findFileByFileName(fileName);
    }

    @Override
    public void saveFile(File file) {
        fileRepository.save(file);
    }

    @Override
    public List<File> findFilesByUserId(Long userId) {
        return fileRepository.findFilesByUserId(userId);
    }

    @Override
    public File findFileById(Integer id) {
        return fileRepository.findFileById(id);
    }

    @Override
    public void deleteFileById(Integer id) {
        fileRepository.deleteById(id);
    }

    @Override
    public File findLastFileByUserId(Long userId) {
        return fileRepository.findLastFileByUserId(userId);
    }
}
