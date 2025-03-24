package lt.restservice.bl.repositories.custom;

import org.springframework.data.domain.Page;

import lt.restservice.bl.models.ImageSearch;
import lt.restservice.bl.models.ThumbnailListDto;

public interface CustomImageRepository {
    Page<ThumbnailListDto> search(ImageSearch imageSearch);
}
