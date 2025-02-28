package lt.restservice.app;

import java.io.IOException;

import lt.restservice.app.mappers.UploadRequestMapper;
import lt.restservice.app.dto.UploadRequest;

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

    private final UploadRequestMapper uploadRequestMapper;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadImage(@RequestPart("dto") UploadRequest dto,
            @RequestPart("imageFile") MultipartFile multipartFile) throws IOException {

        log.debug("new dto: {} {} {}", dto.getImageName(), dto.getAuthorName(),
                dto.getUploadDate());

        uploadRequestMapper.upload(dto, multipartFile);

        return ResponseEntity.ok("Image uploaded successfully");
    }
}
