package com.example.baimoi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

    public FileSystemStorageService(){
        this.rootLocation = Paths.get("DA_CNPM-dat/baimoi/src/main/resources/static/uploads");
    }
    
	@Override
	public String store(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return filename; // Trả về tên file
    }
    @Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
		}
	}
}
