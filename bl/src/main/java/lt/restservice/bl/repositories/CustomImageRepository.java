package lt.restservice.bl.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import lt.restservice.bl.models.ImageModel;
import lt.restservice.model.Image;

public interface CustomImageRepository {

    Page<Image> findBySingleQuery(PageRequest pageRequest, String query);
    Page<Image> findByImageModel(PageRequest pageRequest, ImageModel imageModel);
}
