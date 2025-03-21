package lt.restservice.app.mappers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.ImageSearchDto;
import lt.restservice.app.dto.ImageSearchRequest;
import lt.restservice.app.dto.ThumbnailDto;
import lt.restservice.bl.models.SearchImage;
import lt.restservice.bl.models.ThumbnailDtoList;
import lt.restservice.bl.services.ImageService;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class GalleryPageResponseMapper {

    private final ImageService imageService;

    public Page<ThumbnailDto> toThumbnailDtoPage(ImageSearchRequest imageSearchRequest) {
        SearchImage searchImage = toSearchImage(imageSearchRequest);

        Page<ThumbnailDtoList> thumbnailDtoPage = imageService.createThumbnailDtoPage(searchImage);
        return of(thumbnailDtoPage);
    }

    private static SearchImage toSearchImage(ImageSearchRequest imageSearchRequest) {
        SearchImage.SearchImageBuilder builder = SearchImage.builder()
                .page(imageSearchRequest.getPage())
                .size(imageSearchRequest.getSize())
                .query(imageSearchRequest.getQuery());

        ImageSearchDto imageSearchDto = imageSearchRequest.getImageSearchDto();
        if (imageSearchDto != null) {
            builder = builder.imageName(imageSearchDto.getImageName())
                    .description(imageSearchDto.getDescription())
                    .dateFrom(imageSearchDto.getDateFrom())
                    .dateTo(imageSearchDto.getDateTo())
                    .authorName(imageSearchDto.getAuthorName())
                    .tagNames(imageSearchDto.getTags());
        }
        return builder.build();
    }

    private static Page<ThumbnailDto> of(Page<ThumbnailDtoList> thumbnailDtoPage) {
        return thumbnailDtoPage.map(ThumbnailDto::of);
    }
}
