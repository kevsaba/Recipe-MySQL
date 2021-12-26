package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.repositories.RecipeRepository;
import com.example.recipe.recipe.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    RecipeRepository repository;

    public ImageServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveImageFile(long id, MultipartFile file) {
        var recipe = repository.findById(id).orElse(null);
        if (nonNull(recipe)) {
            try {
                var byteObjects = new Byte[file.getBytes().length];
                int i = 0;
                for (var b : file.getBytes()) { //this is for outofboxing byte[]
                    byteObjects[i++] = b;
                }
                recipe.setImage(byteObjects);
                repository.save(recipe);
            } catch (IOException e) {
                log.error("Error while out of boxing byte[]", e);
                e.printStackTrace();
            }
        }
    }
}
