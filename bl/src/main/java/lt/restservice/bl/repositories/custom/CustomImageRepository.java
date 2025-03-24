package lt.restservice.bl.repositories.custom;

import org.springframework.data.domain.Page;

import lt.restservice.bl.models.ImageView;
import lt.restservice.bl.models.ImageSearch;
import lt.restservice.bl.models.ThumbnailListDto;

public interface CustomImageRepository {
    Page<ThumbnailListDto> findByImageSearchRequest(ImageSearch imageSearch);
    ImageView findImageViewById(Long id);
}
