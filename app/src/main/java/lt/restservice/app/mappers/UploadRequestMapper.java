package lt.restservice.app.mappers;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.UploadRequest;
import lt.restservice.bl.CreateImageModel;
import lt.restservice.bl.ImageService;
import lt.restservice.bl.ThumbnailGenerator;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadRequestMapper {

    private final ImageService imageService;

    @Transactional
    public void upload(UploadRequest dto, MultipartFile multipartFile) throws IOException {

        byte[] thumbnail = ThumbnailGenerator.createThumbnail(multipartFile.getBytes());

        CreateImageModel imageModel = CreateImageModel.builder()
                .imageFile(multipartFile.getBytes())
                .imageName(dto.getImageName())
                .description(dto.getDescription())
                .date(dto.getDate())
                .authorName(dto.getAuthorName())
                .tagNames(dto.getTags())
                .uploadDate(dto.getUploadDate())
                .thumbnail(thumbnail)
                .build();

        imageService.uploadImage(imageModel);
    }
}
