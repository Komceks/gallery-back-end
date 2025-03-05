package lt.restservice.app.mappers;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.GalleryThumbnailDataResponse;
import lt.restservice.bl.ImageService;

@Slf4j
@Component
@RequiredArgsConstructor
public class GalleryThumbnailDataResponseMapper {

    private final ImageService imageService;

    @Transactional
    public List<GalleryThumbnailDataResponse> toGalleryThumbnailDataResponse(int startIdx, int endIdx) {

        return imageService.getImageRequestBatch(startIdx, endIdx).stream().map(createImageModel ->
                GalleryThumbnailDataResponse.builder()
                        .id(createImageModel.getId())
                        .imageName(createImageModel.getImageName())
                        .description(createImageModel.getDescription())
                        .authorName(createImageModel.getAuthorName())
                        .date(createImageModel.getDate())
                        .tags(createImageModel.getTagNames())
                        .uploadDate(createImageModel.getUploadDate())
                        .thumbnail(createImageModel.getThumbnail())
                        .build()
        ).toList();
    }

    @Transactional
    public List<GalleryThumbnailDataResponse> toGalleryThumbnailDataResponse(int startIdx, int endIdx, String query) {

        return imageService.getImageRequestBatch(startIdx, endIdx, query).stream().map(createImageModel ->
                GalleryThumbnailDataResponse.builder()
                        .id(createImageModel.getId())
                        .imageName(createImageModel.getImageName())
                        .description(createImageModel.getDescription())
                        .authorName(createImageModel.getAuthorName())
                        .date(createImageModel.getDate())
                        .tags(createImageModel.getTagNames())
                        .uploadDate(createImageModel.getUploadDate())
                        .thumbnail(createImageModel.getThumbnail())
                        .build()
        ).toList();
    }
}
