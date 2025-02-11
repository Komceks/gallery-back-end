package com.restservice.restservice.app;

import com.restservice.restservice.model.Images;
import com.restservice.restservice.bl.ImagesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("images")
@AllArgsConstructor
public class ImagesController {

    private final ImagesService imagesService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Images savedImage = imagesService.uploadImage(file);
            return ResponseEntity.ok("Image uploaded successfully, ID: " + savedImage.getId());
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload image");
        }
    }

    @GetMapping("/{id}")
    public Optional<Images> getImageById(@PathVariable(value = "id") Long id) {
        return imagesService.getImageById(id);
    }
}
