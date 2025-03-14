package lt.restservice.app.mappers;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.SearchOrUploadRequest;
import lt.restservice.bl.models.ImageModel;
import lt.restservice.bl.services.ImageService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadRequestMapper {

    private final ImageService imageService;

    @Transactional
    public void upload(SearchOrUploadRequest dto, MultipartFile multipartFile) throws IOException {

        LocalDateTime uploadDate = LocalDateTime.now();

        ImageModel imageModel = ImageModel.builder()
                .imageFile(multipartFile.getBytes())
                .imageName(dto.getImageName())
                .description(dto.getDescription())
                .date(dto.getDate())
                .authorName(dto.getAuthorName())
                .tagNames(dto.getTags())
                .uploadDate(uploadDate)
                .build();

        imageService.uploadImage(imageModel);
    }
}
