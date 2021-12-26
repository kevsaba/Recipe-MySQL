package com.example.recipe.recipe.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(long id, MultipartFile file);
}
