package lt.restservice.app.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.ImageSearchRequestPart;
import lt.restservice.app.dto.ImageSearchRequest;
import lt.restservice.app.dto.ThumbnailListDto;
import lt.restservice.bl.model.ImageSearch;
import lt.restservice.bl.model.ImageSearchPart;
import lt.restservice.bl.model.ThumbnailListModel;
import lt.restservice.bl.service.ImageService;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class GalleryPageResponseMapper {

    private final ImageService imageService;

    public Page<ThumbnailListDto> toThumbnailListDtoPage(ImageSearchRequest imageSearchRequest) {
        ImageSearch imageSearch = toSearchImage(imageSearchRequest);
        Page<ThumbnailListModel> thumbnailListDtoPage = imageService.search(imageSearch);

        return of(thumbnailListDtoPage);
    }

    private static ImageSearch toSearchImage(ImageSearchRequest imageSearchRequest) {
        ImageSearch.ImageSearchBuilder builder = ImageSearch.builder()
                .pageNumber(imageSearchRequest.getPageNumber())
                .pageSize(imageSearchRequest.getPageSize())
                .query(imageSearchRequest.getQuery());
        ImageSearchRequestPart imageSearchRequestPart = imageSearchRequest.getImageSearchRequestPart();

        if (imageSearchRequestPart != null) {
            ImageSearchPart imageSearchPart = ImageSearchPart.builder()
                    .imageName(imageSearchRequestPart.getImageName())
                    .description(imageSearchRequestPart.getDescription())
                    .dateFrom(imageSearchRequestPart.getDateFrom())
                    .dateTo(imageSearchRequestPart.getDateTo())
                    .authorName(imageSearchRequestPart.getAuthorName())
                    .tagNames(imageSearchRequestPart.getTags())
                    .build();

            builder = builder.imageSearchPart(imageSearchPart);
        }

        return builder.build();
    }

    private static Page<ThumbnailListDto> of(Page<ThumbnailListModel> thumbnailListDtoPage) {
        return thumbnailListDtoPage.map(ThumbnailListDto::of);
    }
}
