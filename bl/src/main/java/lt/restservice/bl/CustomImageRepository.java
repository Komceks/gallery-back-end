package lt.restservice.bl;

import java.util.List;

import lt.restservice.model.Image;

public interface CustomImageRepository {
    List<Image> searchImages(int startIdx, int endIdx, String query);
}
