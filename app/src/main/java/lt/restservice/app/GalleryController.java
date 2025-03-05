package lt.restservice.app;

import java.io.IOException;
import java.util.List;

import lt.restservice.app.dto.GalleryThumbnailDataResponse;
import lt.restservice.app.dto.UploadRequest;

import lt.restservice.app.mappers.UploadRequestMapper;
import lt.restservice.app.mappers.GalleryThumbnailDataResponseMapper;

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
    private final GalleryThumbnailDataResponseMapper galleryThumbnailDataResponseMapper;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadImage(@RequestPart("dto") UploadRequest dto,
            @RequestPart("imageFile") MultipartFile multipartFile) throws IOException {

        log.debug("new dto: {} {}", dto.getImageName(), dto.getAuthorName());

        uploadRequestMapper.upload(dto, multipartFile);

        return ResponseEntity.ok("Image uploaded successfully");
    }

    @GetMapping
    public ResponseEntity<List<GalleryThumbnailDataResponse>> getImageBatch(
            @RequestParam(value = "startIdx") int startIdx,
            @RequestParam(value = "endIdx") int endIdx) {

        List<GalleryThumbnailDataResponse> GalleryThumbnailDataResponse =
                galleryThumbnailDataResponseMapper.toGalleryThumbnailDataResponse(startIdx, endIdx);

        return ResponseEntity.ok(GalleryThumbnailDataResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GalleryThumbnailDataResponse>> search(
            @RequestParam(value = "startIdx") int startIdx,
            @RequestParam(value = "endIdx") int endIdx,
            @RequestParam(value = "search") String query) {

        List<GalleryThumbnailDataResponse> GalleryThumbnailDataResponse =
                galleryThumbnailDataResponseMapper.toGalleryThumbnailDataResponse(startIdx, endIdx, query);

        return ResponseEntity.ok(GalleryThumbnailDataResponse);
    }
}
