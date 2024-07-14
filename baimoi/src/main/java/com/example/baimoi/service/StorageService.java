package com.example.baimoi.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;



public interface StorageService {
    void init();
    String store(MultipartFile file) throws IOException;
   
}