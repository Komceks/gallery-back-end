package lt.restservice.app.mapper;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.ImageUploadRequest;
import lt.restservice.bl.model.CreateImageModel;
import lt.restservice.bl.service.ImageService;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UploadRequestMapper {

    private final ImageService imageService;

    public void upload(ImageUploadRequest dto, MultipartFile multipartFile) throws IOException {
        LocalDateTime uploadDate = LocalDateTime.now();
        CreateImageModel createImageModel = CreateImageModel.builder()
                .imageFile(multipartFile.getBytes())
                .imageName(dto.getImageName())
                .description(dto.getDescription())
                .date(dto.getDate())
                .authorName(dto.getAuthorName())
                .tagNames(dto.getTags())
                .uploadDate(uploadDate)
                .build();
        imageService.uploadImage(createImageModel);
    }
}
