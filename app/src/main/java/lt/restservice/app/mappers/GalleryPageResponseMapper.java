package lt.restservice.app.mappers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.ImageViewResponse;
import lt.restservice.app.dto.ImageSearchDto;
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

    private static Page<ThumbnailDto> of(Page<ThumbnailListDto> thumbnailListDtoPage) {
        return thumbnailListDtoPage.map(ThumbnailDto::of);
    }

    @Transactional
    public ImageViewResponse toImageViewResponse(Long id) {
        return ImageViewResponse.of(imageService.createImageViewById(id));
    }

}
