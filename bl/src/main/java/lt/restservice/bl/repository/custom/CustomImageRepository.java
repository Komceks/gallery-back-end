package lt.restservice.bl.repository.custom;

import org.springframework.data.domain.Page;

import lt.restservice.bl.model.ImageSearch;
import lt.restservice.bl.model.ImageView;
import lt.restservice.bl.model.ThumbnailListModel;

public interface CustomImageRepository {
    Page<ThumbnailListModel> search(ImageSearch imageSearch);
    ImageView viewImage(Long id);
}
