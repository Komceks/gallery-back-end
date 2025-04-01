package lt.restservice.bl.repository.custom;

import org.springframework.data.domain.Page;

import lt.restservice.bl.model.ImageSearchModel;
import lt.restservice.bl.model.ImageViewModel;
import lt.restservice.bl.model.ThumbnailListModel;

public interface CustomImageRepository {
    Page<ThumbnailListModel> search(ImageSearchModel imageSearchModel);
    ImageViewModel view(Long id);
}
