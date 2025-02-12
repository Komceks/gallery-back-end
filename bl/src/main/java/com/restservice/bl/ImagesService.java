package com.restservice.bl;

import com.restservice.model.Images;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
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
