package com.medulasales.products.utils.storage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileStorageManager {

    private final Path rootLocation;

    public void requirePath(Path path) {
        try {
            if(!Files.exists(path)) {
                Files.createDirectories(path);
            }
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage path", e);
        }
    }

    public FileStorageManager(String rootLocation) {
        this.rootLocation = Paths.get(rootLocation);
    }

    public void store(String filename, MultipartFile file) {
        try {
            if(filename == null) {
                throw new StorageException("File setName cannot be null");
            }
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new StorageException("Cannot store media with relative path outside current directory " + filename);
            }
            file.transferTo(this.rootLocation.resolve(filename));
        }
        catch (IOException e) {
            throw new StorageException("Failed to store media " + filename, e);
        }
    }
    public void store(MultipartFile file) {
        store(file.getOriginalFilename(), file);
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read media: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read media: " + filename, e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    public void init() {
        requirePath(rootLocation);
    }
}
