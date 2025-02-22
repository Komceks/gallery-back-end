package lt.restservice.app;

import java.io.IOException;
import java.util.List;

import lt.restservice.bl.ImageService;
import lt.restservice.bl.ImageUploadDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@ModelAttribute ImageUploadDto dto) throws IOException {

        imageService.uploadImage(dto);

        return ResponseEntity.ok("Image uploaded successfully");
    }

}
