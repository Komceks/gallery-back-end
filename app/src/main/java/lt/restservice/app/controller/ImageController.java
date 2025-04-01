package lt.restservice.app.controller;

import java.io.IOException;

import lt.restservice.app.dto.ImageSaveRequest;
import lt.restservice.app.dto.ImageUpdateResponse;
import lt.restservice.app.dto.ImageViewResponse;
import jakarta.validation.Valid;
import lt.restservice.app.dto.ImageSearchRequest;
import lt.restservice.app.dto.ThumbnailListDto;

import lt.restservice.app.mapper.ImageMapper;

import lombok.RequiredArgsConstructor;
import lt.restservice.app.marker.CreateRequest;
import lt.restservice.app.marker.UpdateRequest;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageMapper imageMapper;

    @Validated(CreateRequest.class)
    @PostMapping(value = "/upload")
    public void upload(
            @Valid @RequestPart("dto") ImageSaveRequest dto,
            @RequestPart("imageFile") MultipartFile multipartFile
    ) throws IOException {
        imageMapper.upload(dto, multipartFile);
    }

    @PostMapping("/search")
    public Page<ThumbnailListDto> search(@Valid @RequestBody ImageSearchRequest dto) {
        return imageMapper.search(dto);
    }

    @GetMapping("/{id}")
    public ImageViewResponse view(@PathVariable("id") Long id) {
        return imageMapper.view(id);
    }

    @Validated(UpdateRequest.class)
    @PostMapping(value = "/update")
    public ImageUpdateResponse update(
            @Valid @RequestPart("dto") ImageSaveRequest dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile multipartFile
    ) throws IOException {
        return imageMapper.update(dto, multipartFile);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        imageMapper.delete(id);
    }
}
