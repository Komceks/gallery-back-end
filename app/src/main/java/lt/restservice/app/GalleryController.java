package lt.restservice.app;

import java.io.IOException;
import java.util.List;

import lt.restservice.app.dto.ThumbnailRetrieveDto;
import lt.restservice.app.mappers.UploadRequestMapper;
import lt.restservice.app.dto.UploadRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.restservice.app.mappers.ThumbnailRetriveMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final UploadRequestMapper uploadRequestMapper;
    private final ThumbnailRetriveMapper thumbnailRetriveMapper;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadImage(@RequestPart("dto") UploadRequest dto,
            @RequestPart("imageFile") MultipartFile multipartFile) throws IOException {

        log.debug("new dto: {} {} {}", dto.getImageName(), dto.getAuthorName(),
                dto.getUploadDate());

        uploadRequestMapper.upload(dto, multipartFile);

        return ResponseEntity.ok("Image uploaded successfully");
    }

    @GetMapping
    public ResponseEntity<List<ThumbnailRetrieveDto>> getImageBatch(@RequestParam(value = "startIdx") int startIdx,
            @RequestParam(value = "endIdx") int endIdx) {

        List<ThumbnailRetrieveDto> thumbnailRetrieveDtos = thumbnailRetriveMapper.toThumbnailRetrieveDto(startIdx, endIdx);

        return ResponseEntity.ok(thumbnailRetrieveDtos);
    }
}
