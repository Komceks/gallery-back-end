package lt.restservice.app;

import java.io.IOException;

import lt.restservice.app.dto.ImageViewResponse;
import jakarta.validation.Valid;
import lt.restservice.app.dto.ImageSearchRequest;
import lt.restservice.app.dto.ThumbnailListDto;
import lt.restservice.app.dto.ImageUploadRequest;

import lt.restservice.app.mapper.UploadRequestMapper;
import lt.restservice.app.mapper.GalleryPageResponseMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final UploadRequestMapper uploadRequestMapper;
    private final GalleryPageResponseMapper galleryPageResponseMapper;

    @PostMapping(value = "/upload")
    public ResponseEntity<Void> uploadImage(@Valid @RequestPart("dto") ImageUploadRequest dto, @RequestPart("imageFile") MultipartFile multipartFile) throws IOException {
        uploadRequestMapper.upload(dto, multipartFile);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public Page<ThumbnailListDto> search(@Valid @RequestBody ImageSearchRequest dto) {
        return galleryPageResponseMapper.toThumbnailListDtoPage(dto);
    }

    @GetMapping("image/{id}")
    public ImageViewResponse viewImage(@PathVariable("id") Long id) {
        return galleryPageResponseMapper.toImageViewResponse(id);
    }
}
