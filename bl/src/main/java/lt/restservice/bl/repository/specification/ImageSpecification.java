package lt.restservice.bl.repository.specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lt.restservice.bl.model.ImageSearchModel;
import lt.restservice.bl.model.ImageSearchPartModel;
import lt.restservice.bl.utils.CriteriaQueryUtils;
import lt.restservice.model.Author;
import lt.restservice.model.Author_;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;
import lt.restservice.model.Tag;
import lt.restservice.model.Tag_;

public class ImageSpecification {
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static Specification<Image> buildSpecification(ImageSearchModel imageSearchModel) {
        if (imageSearchModel.getQuery() != null) {
            if (StringUtils.isNotBlank(imageSearchModel.getQuery())) {
                return ImageSpecification.buildSpecification(imageSearchModel.getQuery());
            }
            return ImageSpecification.buildSpecification("");
        }

        return ImageSpecification.buildSpecification(imageSearchModel.getImageSearchPartModel());
    }

    public static Specification<Image> buildSpecification(Long id) {
        return ImageSpecBuilder.builder()
                .id(id)
                .build();
    }

    private static Specification<Image> buildSpecification(ImageSearchPartModel imageSearchPartModel) {
        return ImageSpecBuilder.builder()
                .name(imageSearchPartModel.getImageName())
                .description(imageSearchPartModel.getDescription())
                .author(imageSearchPartModel.getAuthorName())
                .dateFrom(imageSearchPartModel.getDateFrom())
                .dateTo(imageSearchPartModel.getDateTo())
                .tagsIn(imageSearchPartModel.getTagNames())
                .build();
    }

    private static Specification<Image> buildSpecification(String queryString) {
        return Arrays.stream(queryString.split(WHITESPACE_REGEX))
                .map(ImageSpecification::buildTermSpecification)
                .reduce(null, CriteriaQueryUtils::and);
    }

    private static Specification<Image> buildTermSpecification(String term) {
        ImageSpecBuilder imageSpecBuilder = ImageSpecBuilder.builder()
                .orName(term)
                .orDescription(term)
                .orAuthor(term)
                .tagsEqual(term);

        if (term.matches(DATE_REGEX)) {
            imageSpecBuilder = imageSpecBuilder.orDate(LocalDate.parse(term));
        }

        return imageSpecBuilder.build();
    }

    private static class ImageSpecBuilder {
        private Specification<Image> specification;

        private ImageSpecBuilder() {
            this.specification = null;
        }

        public static ImageSpecBuilder builder() {
            return new ImageSpecBuilder();
        }

        public Specification<Image> build() {
            return this.specification == null ?
                    (root, query, cb) -> cb.conjunction() : this.specification;
        }

        public ImageSpecBuilder id(Long id) {
            if (id != null) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) ->
                                cb.equal(root.get(Image_.id), id)
                );
            }

            return this;
        }

        public ImageSpecBuilder name(String name) {
            if (StringUtils.isNotBlank(name)) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) ->
                                CriteriaQueryUtils.buildLikePatternPredicate(root.get(Image_.name), name, cb)
                );
            }

            return this;
        }

        public ImageSpecBuilder orName(String name) {
            if (StringUtils.isNotBlank(name)) {
                this.specification = CriteriaQueryUtils.or(this.specification,
                        (root, query, cb) ->
                                CriteriaQueryUtils.buildLikePatternPredicate(root.get(Image_.name), name, cb)
                );
            }

            return this;
        }

        public ImageSpecBuilder description(String description) {
            if (StringUtils.isNotBlank(description)) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) ->
                                CriteriaQueryUtils.buildLikePatternPredicate(root.get(Image_.description), description, cb)
                );
            }

            return this;
        }

        public ImageSpecBuilder orDescription(String description) {
            if (StringUtils.isNotBlank(description)) {
                this.specification = CriteriaQueryUtils.or(this.specification,
                        (root, query, cb) ->
                                CriteriaQueryUtils.buildLikePatternPredicate(root.get(Image_.description), description, cb)
                );
            }

            return this;
        }

        public ImageSpecBuilder author(String author) {
            if (StringUtils.isNotBlank(author)) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) -> {
                            Join<Image, Author> authorJoin = root.join(Image_.author);
                            return CriteriaQueryUtils.buildLikePatternPredicate(authorJoin.get(Author_.name), author, cb);
                        });
            }

            return this;
        }

        public ImageSpecBuilder orAuthor(String author) {
            if (StringUtils.isNotBlank(author)) {
                this.specification = CriteriaQueryUtils.or(this.specification,
                        (root, query, cb) -> {
                            Join<Image, Author> authorJoin = root.join(Image_.author);
                            return CriteriaQueryUtils.buildLikePatternPredicate(authorJoin.get(Author_.name), author, cb);
                        });
            }

            return this;
        }

        public ImageSpecBuilder orDate(LocalDate date) {
            this.specification = CriteriaQueryUtils.or(this.specification,
                    (root, query, cb) ->
                            cb.equal(root.get(Image_.date), date)
            );

            return this;
        }

        public ImageSpecBuilder dateFrom(LocalDate dateFrom) {
            if (dateFrom != null) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) ->
                                cb.greaterThanOrEqualTo(root.get(Image_.date), dateFrom)
                );
            }
            return this;
        }

        public ImageSpecBuilder dateTo(LocalDate dateTo) {
            if (dateTo != null) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) ->
                                cb.lessThanOrEqualTo(root.get(Image_.date), dateTo)
                );
            }
            return this;
        }

        public ImageSpecBuilder tagsIn(Set<String> tags) {
            if (tags != null && !tags.isEmpty()) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) -> {
                            Join<Image, Tag> tagSetJoin = root.join(Image_.tags);
                            return tagSetJoin.get(Tag_.name).in(tags);
                        });
            }

            return this;
        }

        public ImageSpecBuilder tagsEqual(String tag) {
            if (StringUtils.isNotBlank(tag)) {
                this.specification = CriteriaQueryUtils.or(this.specification,
                        (root, query, cb) -> {
                            Join<Image, Tag> tagSetJoin = root.join(Image_.tags, JoinType.LEFT);
                            return cb.equal(cb.lower(tagSetJoin.get(Tag_.name)), tag.toLowerCase());
                        });
            }

            return this;
        }
    }
}
