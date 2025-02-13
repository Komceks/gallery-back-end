package lt.restservice.app;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import lt.restservice.bl.ImageService;
import lt.restservice.bl.ImageUploadDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class GalleryController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("author") String author,
            @RequestParam("date") Date date,
            @RequestParam("tags") List<String> tags,
            @RequestParam("image") MultipartFile image
    ) {
        byte[] imageBytes;

        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to process image");
        }

        ImageUploadDto dto = new ImageUploadDto(name, description, author, date, imageBytes, tags);

        imageService.uploadImage(dto);

        return ResponseEntity.ok("Image uploaded successfully");

    }

}
