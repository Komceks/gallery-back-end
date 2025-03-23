package lt.restservice.bl.repositories.specifications;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import lt.restservice.bl.models.ImageSearch;
import lt.restservice.bl.utils.CriteriaQueryUtils;
import lt.restservice.model.Image;

public class ImageSpecifications {

    private static final String WHITESPACE_REGEX = "\\s+";

    public static Specification<Image> imageSpecifications(ImageSearch imageSearch) {
        return ImageSpecBuilder.builder()
                .name(imageSearch.getImageName())
                .description(imageSearch.getDescription())
                .author(imageSearch.getAuthorName())
                .dateFrom(imageSearch.getDateFrom())
                .dateTo(imageSearch.getDateTo())
                .tagsIn(imageSearch.getTagNames())
                .build();
    }

    public static Specification<Image> imageSpecifications(String queryString) {
        return Arrays.stream(queryString.split(WHITESPACE_REGEX))
                .map(ImageSpecifications::buildImageSpecification)
                .reduce(null, CriteriaQueryUtils::and);
    }

    private static Specification<Image> buildImageSpecification(String word) {
        return ImageSpecBuilder.builder()
                .orName(word)
                .orDescription(word)
                .orAuthor(word)
                .orDate(word)
                .tagsEqual(word)
                .build();
    }

    public static Specification<Image> buildImageSpecification(ImageSearch imageSearch) {

        if (StringUtils.isNotBlank(imageSearch.getQuery())) {
            return ImageSpecifications.imageSpecifications(imageSearch.getQuery());
        }

        return ImageSpecifications.imageSpecifications(imageSearch);
    }
}
