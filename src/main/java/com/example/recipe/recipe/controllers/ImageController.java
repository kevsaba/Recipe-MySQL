package com.example.recipe.recipe.controllers;

import com.example.recipe.recipe.services.ImageService;
import com.example.recipe.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.nonNull;

@Slf4j
@Controller
public class ImageController {

    ImageService imageService;
    RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/image")
    public String getUploadRecipeImageForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/imageform";
    }

    @PostMapping("/recipe/{id}/image")
    public String uploadRecipeImage(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(id, file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("/recipe/{id}/recipeimage")
    public void renderImageFromDb(@PathVariable Long id, HttpServletResponse response) throws IOException {
        var command = recipeService.findCommandById(id);
        Byte[] image = command.getImage();
        if (nonNull(image)) {
            byte[] img = new byte[image.length];
            int i = 0;
            for (var b : image) {
                img[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(img);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
