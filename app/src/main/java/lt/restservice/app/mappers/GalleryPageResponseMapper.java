package lt.restservice.app.mappers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.ImageSearchPart;
import lt.restservice.app.dto.ImageSearchRequest;
import lt.restservice.app.dto.ThumbnailDto;
import lt.restservice.bl.models.ImageSearch;
import lt.restservice.bl.models.ThumbnailListDto;
import lt.restservice.bl.services.ImageService;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class GalleryPageResponseMapper {

    private final ImageService imageService;

    public Page<ThumbnailDto> toThumbnailDtoPage(ImageSearchRequest imageSearchRequest) {
        ImageSearch imageSearch = toSearchImage(imageSearchRequest);

        Page<ThumbnailListDto> thumbnailListDtoPage = imageService.createThumbnailListDtoPage(imageSearch);
        return of(thumbnailListDtoPage);
    }

    private static ImageSearch toSearchImage(ImageSearchRequest imageSearchRequest) {
        ImageSearch.ImageSearchBuilder builder = ImageSearch.builder()
                .pageNumber(imageSearchRequest.getPageNumber())
                .pageSize(imageSearchRequest.getPageSize())
                .query(imageSearchRequest.getQuery());

        ImageSearchPart imageSearchPart = imageSearchRequest.getImageSearchPart();
        if (imageSearchPart != null) {
            builder = builder.imageName(imageSearchPart.getImageName())
                    .description(imageSearchPart.getDescription())
                    .dateFrom(imageSearchPart.getDateFrom())
                    .dateTo(imageSearchPart.getDateTo())
                    .authorName(imageSearchPart.getAuthorName())
                    .tagNames(imageSearchPart.getTags());
        }
        return builder.build();
    }

    private static Page<ThumbnailDto> of(Page<ThumbnailListDto> thumbnailListDtoPage) {
        return thumbnailListDtoPage.map(ThumbnailDto::of);
    }
}
