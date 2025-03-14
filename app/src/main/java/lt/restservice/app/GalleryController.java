package lt.restservice.app;

import java.io.IOException;

import lt.restservice.app.dto.ThumbnailDto;
import lt.restservice.app.dto.SearchOrUploadRequest;

import lt.restservice.app.mappers.UploadRequestMapper;
import lt.restservice.app.mappers.GalleryPageResponseMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@RestController
@RequestMapping("gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final UploadRequestMapper uploadRequestMapper;
    private final GalleryPageResponseMapper galleryPageResponseMapper;

    @PostMapping(value = "/upload")
    public ResponseEntity<Void> uploadImage(@RequestPart("dto") SearchOrUploadRequest dto,
            @RequestPart("imageFile") MultipartFile multipartFile) throws IOException {

        uploadRequestMapper.upload(dto, multipartFile);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ThumbnailDto>> getImagePage(@RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size) {

        Page<ThumbnailDto> galleryPageResponse =
                galleryPageResponseMapper.toGalleryPageResponse(page, size);

        return ResponseEntity.ok(galleryPageResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ThumbnailDto>> search(@RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "query") String query) {

        Page<ThumbnailDto> galleryPageResponse =
                galleryPageResponseMapper.toGalleryPageResponse(page, size, query);

        return ResponseEntity.ok(galleryPageResponse);
    }

    @GetMapping("/advanced_search")
    public ResponseEntity<Page<ThumbnailDto>> advancedSearch(@RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "dto") String dto) throws JsonProcessingException {

        SearchOrUploadRequest searchRequest = new ObjectMapper().readValue(dto, SearchOrUploadRequest.class);

        Page<ThumbnailDto> galleryPageResponse =
                galleryPageResponseMapper.toGalleryPageResponse(page, size, searchRequest);

        return ResponseEntity.ok(galleryPageResponse);
    }
}
