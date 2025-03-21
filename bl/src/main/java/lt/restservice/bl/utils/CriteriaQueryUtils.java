package lt.restservice.bl.utils;

import org.springframework.data.jpa.domain.Specification;

import lt.restservice.bl.models.SearchImage;
import lt.restservice.bl.repositories.specifications.ImageSpecifications;
import lt.restservice.model.Image;

public class CriteriaQueryUtils {

    public static String toLikePattern(String token) {
        return "%" + token.toLowerCase() + "%";
    }

    public static Specification<Image> buildImageSpecification(SearchImage searchImage) {
        if (searchImage.getQuery() != null) {
            return ImageSpecifications.imageSpecifications(searchImage.getQuery());
        } else {
            return ImageSpecifications.imageSpecifications(searchImage);
        }
    }
}
