package com.github.wxiaoqi.oss.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {


    @Autowired
    private StorageProperties storageProperties;


    @Override
    public String store(MultipartFile file){
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            String uploadPath = storageProperties.getUploadPath();
            File newFile = new File(uploadPath, file.getOriginalFilename());
            String fileName = file.getOriginalFilename();
            String[] fileNameArr = fileName.split("\\.");
            if(newFile.exists()){
                fileName = fileNameArr[0] +"-" +System.currentTimeMillis() + "."+fileNameArr[1];
            }else{
                fileName = fileNameArr[0] +"-" +System.currentTimeMillis() + "."+fileNameArr[1];
            }

//            Path imageUrl = Paths.get(uploadPath).resolve(fileName);
            Files.copy(file.getInputStream(), Paths.get(uploadPath).resolve(fileName));

            return uploadPath+fileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(Paths.get(storageProperties.getUploadPath()), 1)
                    .filter(path -> !path.equals(Paths.get(storageProperties.getUploadPath())))
                    .map(path ->Paths.get(storageProperties.getUploadPath()).relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return Paths.get(storageProperties.getUploadPath()).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(storageProperties.getUploadPath()).toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(Paths.get(storageProperties.getUploadPath()));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public File buildXlsById(){
        File file=null;
        try {
            file = ResourceUtils.getFile("classpath:static/test.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }
}
