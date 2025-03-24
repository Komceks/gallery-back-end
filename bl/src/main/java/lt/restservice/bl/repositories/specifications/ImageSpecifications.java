package lt.restservice.bl.repositories.specifications;

import java.time.LocalDate;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import lt.restservice.bl.models.ImageSearch;
import lt.restservice.bl.utils.CriteriaQueryUtils;
import lt.restservice.model.Image;

public class ImageSpecifications {
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

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
        ImageSpecBuilder imageSpecBuilder = ImageSpecBuilder.builder()
                .orName(word)
                .orDescription(word)
                .orAuthor(word)
                .tagsEqual(word);

        if (word.matches(DATE_REGEX)) {
            imageSpecBuilder = imageSpecBuilder.orDate(LocalDate.parse(word));
        }

        return imageSpecBuilder.build();
    }

    public static Specification<Image> buildImageSpecification(ImageSearch imageSearch) {

        if (StringUtils.isNotBlank(imageSearch.getQuery())) {
            return ImageSpecifications.imageSpecifications(imageSearch.getQuery());
        }

        return ImageSpecifications.imageSpecifications(imageSearch);
    }
}
