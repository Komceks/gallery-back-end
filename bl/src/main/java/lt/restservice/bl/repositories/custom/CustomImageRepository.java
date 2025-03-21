package lt.restservice.bl.repositories.custom;

import org.springframework.data.domain.Page;

import lt.restservice.bl.models.SearchImage;
import lt.restservice.bl.models.ThumbnailDtoList;

public interface CustomImageRepository {
    Page<ThumbnailDtoList> findByImageSearchRequest(SearchImage searchImage);
}
