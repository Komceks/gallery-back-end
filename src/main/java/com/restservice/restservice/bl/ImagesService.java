package com.restservice.restservice.bl;

import com.restservice.restservice.model.Images;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImagesService {


    private final ImagesRepository imagesRepository;

    public Images uploadImage(MultipartFile file) throws IOException {
        Images image = new Images(file.getOriginalFilename(), file.getBytes());
        return imagesRepository.save(image);
    }

    public Optional<Images> getImageById(Long id) {
        return imagesRepository.findById(id);
    }
}
