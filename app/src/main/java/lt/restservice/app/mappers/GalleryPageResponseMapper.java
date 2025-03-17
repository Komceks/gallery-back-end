package lt.restservice.app.mappers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.OpenImageResponse;
import lt.restservice.app.dto.ThumbnailDto;
import lt.restservice.app.dto.SearchOrUploadRequest;
import lt.restservice.bl.models.ImageModel;
import lt.restservice.bl.services.ImageService;

@Slf4j
@Component
@RequiredArgsConstructor
public class GalleryPageResponseMapper {

    private final ImageService imageService;

    @Transactional
    public Page<ThumbnailDto> toGalleryPageResponse(int page, int size) {

        Page<ImageModel> imageModelPage = imageService.getImagePage(page, size);

        return toThumbnailDtoPage(imageModelPage);
    }

    @Transactional
    public Page<ThumbnailDto> toGalleryPageResponse(int page, int size, String query) {

        Page<ImageModel> imageModelPage = imageService.getImagePage(page, size, query);

        return toThumbnailDtoPage(imageModelPage);
    }

    @Transactional
    public Page<ThumbnailDto> toGalleryPageResponse(int page, int size, SearchOrUploadRequest searchRequest) {

        ImageModel imageModel = ImageModel.builder()
                .imageName(searchRequest.getImageName())
                .description(searchRequest.getDescription())
                .date(searchRequest.getDate())
                .authorName(searchRequest.getAuthorName())
                .tagNames(searchRequest.getTags())
                .build();

        Page<ImageModel> imageModelPage = imageService.getImagePage(page, size, imageModel);

        return toThumbnailDtoPage(imageModelPage);
    }

    @Transactional
    protected Page<ThumbnailDto> toThumbnailDtoPage(Page<ImageModel> imageModelPage) {
        return imageModelPage.map(imageModel ->
                ThumbnailDto.builder()
                        .id(imageModel.getId())
                        .imageName(imageModel.getImageName())
                        .description(imageModel.getDescription())
                        .authorName(imageModel.getAuthorName())
                        .date(imageModel.getDate())
                        .thumbnail(imageModel.getThumbnail())
                        .build());
    }

    @Transactional
    public OpenImageResponse toOpenImageResponse(Long id) {

        ImageModel imageModel = imageService.getImageById(id);

        return OpenImageResponse.builder()
                .image(imageModel.getImageFile())
                .tags(imageModel.getTagNames())
                .build();
    }

}
