package lt.restservice.bl.repositories.specifications;

import java.time.LocalDate;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

import lt.restservice.bl.models.SearchImage;
import lt.restservice.bl.utils.CriteriaQueryUtils;
import lt.restservice.model.Author;
import lt.restservice.model.Author_;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;
import lt.restservice.model.Tag;
import lt.restservice.model.Tag_;

public class ImageSpecifications {

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static Specification<Image> imageSpecifications(SearchImage searchImage) {
        Specification<Image> specificationConjunction = (root, query, cb) -> cb.conjunction();
        if (!StringUtils.isBlank(searchImage.getImageName())) {
            specificationConjunction = specificationConjunction.and(name(searchImage.getImageName()));
        }
        if (!StringUtils.isBlank(searchImage.getDescription())) {
            specificationConjunction = specificationConjunction.and(description(searchImage.getDescription()));
        }
        if (!StringUtils.isBlank(searchImage.getAuthorName())) {
            specificationConjunction = specificationConjunction.and(author(searchImage.getAuthorName()));
        }
        if (searchImage.getDateFrom() != null) {
            specificationConjunction = specificationConjunction.and(dateFrom(searchImage.getDateFrom()));
        }
        if (searchImage.getDateTo() != null) {
            specificationConjunction = specificationConjunction.and(dateTo(searchImage.getDateTo()));
        }
        if (CollectionUtils.isNotEmpty(searchImage.getTagNames())) {
            specificationConjunction = specificationConjunction.and(tagsIn(searchImage.getTagNames()));
        }
        return specificationConjunction;
    }

    public static Specification<Image> imageSpecifications(String queryString) {
        Specification<Image> specificationConjunction = null;
        String[] words = queryString.split(WHITESPACE_REGEX);
        for (String word : words) {
            Specification<Image> specificationDisjunction = null;

            specificationDisjunction = orSpecification(specificationDisjunction, name(word));
            specificationDisjunction = orSpecification(specificationDisjunction, description(word));
            specificationDisjunction = orSpecification(specificationDisjunction, author(word));
            if (word.matches(DATE_REGEX)) {
                specificationDisjunction = orSpecification(specificationDisjunction, dateEqual(word));
            }
            specificationDisjunction = orSpecification(specificationDisjunction, tagsEqual(word));

            specificationConjunction = andSpecification(specificationConjunction, specificationDisjunction);
        }
        return specificationConjunction;
    }

    private static Specification<Image> orSpecification(Specification<Image> specification, Specification<Image> newSpecification) {
        return specification == null ? newSpecification : specification.or(newSpecification);
    }

    private static Specification<Image> andSpecification(Specification<Image> specification, Specification<Image> newSpecification) {
        return specification == null ? newSpecification : specification.and(newSpecification);
    }

    public static Specification<Image> name(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Image_.name)), CriteriaQueryUtils.toLikePattern(name));
    }

    public static Specification<Image> description(String description) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Image_.description)), CriteriaQueryUtils.toLikePattern(description));
    }

    public static Specification<Image> author(String author) {
        return (root, query, cb) -> {
            Join<Image, Author> authorJoin = root.join(Image_.author);
            return cb.like(cb.lower(authorJoin.get(Author_.name)), CriteriaQueryUtils.toLikePattern(author));
        };
    }

    public static Specification<Image> dateEqual(String date) {
        return (root, query, cb) -> cb.equal(root.get(Image_.date), LocalDate.parse(date));
    }

    public static Specification<Image> dateFrom(LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Image_.date), date);
    }

    public static Specification<Image> dateTo(LocalDate date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(Image_.date), date);
    }

    public static Specification<Image> tagsIn(Set<String> tags) {
        return (root, query, cb) -> {
            Join<Image, Tag> tagSetJoin = root.join(Image_.tags);
            return tagSetJoin.get(Tag_.name).in(tags);
        };
    }

    public static Specification<Image> tagsEqual(String tag) {
        return (root, query, cb) -> {
            Join<Image, Tag> tagSetJoin = root.join(Image_.tags, JoinType.LEFT);
            return cb.equal(cb.lower(tagSetJoin.get(Tag_.name)), tag.toLowerCase());
        };
    }
}
