package lt.restservice.app;

import java.io.IOException;

import lt.restservice.bl.ImageService;
import lt.restservice.bl.ImageUploadDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final ImageService imageService;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadImage(@RequestPart("dto") ImageUploadDto dto,
            @RequestPart("imageFile") MultipartFile multipartFile) throws IOException {

        log.debug("new dto: {} {} {}", dto.getImageName(), dto.getAuthorName(),
                dto.getUploadDate());

        imageService.uploadImage(dto, multipartFile);

        return ResponseEntity.ok("Image uploaded successfully");
    }

}
