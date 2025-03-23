package lt.restservice.bl.repositories.specifications;

import java.time.LocalDate;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

import lombok.extern.slf4j.Slf4j;

import lt.restservice.bl.utils.CriteriaQueryUtils;
import lt.restservice.model.Author;
import lt.restservice.model.Author_;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;
import lt.restservice.model.Tag;
import lt.restservice.model.Tag_;

@Slf4j
public class ImageSpecBuilder {

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private Specification<Image> specification;

    private ImageSpecBuilder() {
        this.specification = null;
    }

    public static ImageSpecBuilder builder() {
        return new ImageSpecBuilder();
    }

    public Specification<Image> build() {
        return this.specification == null ? (root, query, cb) -> cb.conjunction() : this.specification;
    }

    public ImageSpecBuilder name(String name) {

        if (StringUtils.isNotBlank(name)) {
            log.debug("name {}", name);
            this.specification = CriteriaQueryUtils.and(this.specification,
                    (root, query, cb) ->
                            cb.like(cb.lower(root.get(Image_.name)), CriteriaQueryUtils.toLikePattern(name))
            );
        }

        return this;
    }

    public ImageSpecBuilder orName(String name) {

        if (StringUtils.isNotBlank(name)) {
            this.specification = CriteriaQueryUtils.or(this.specification,
                    (root, query, cb) ->
                            cb.like(cb.lower(root.get(Image_.name)), CriteriaQueryUtils.toLikePattern(name))
            );
        }

        return this;
    }

    public ImageSpecBuilder description(String description) {

        if (StringUtils.isNotBlank(description)) {
            log.debug("description {}", description);
            this.specification = CriteriaQueryUtils.and(this.specification,
                    (root, query, cb) ->
                            cb.like(cb.lower(root.get(Image_.description)), CriteriaQueryUtils.toLikePattern(description))
            );
        }

        return this;
    }

    public ImageSpecBuilder orDescription(String description) {

        if (StringUtils.isNotBlank(description)) {
            this.specification = CriteriaQueryUtils.or(this.specification,
                    (root, query, cb) ->
                            cb.like(cb.lower(root.get(Image_.description)), CriteriaQueryUtils.toLikePattern(description))
            );
        }

        return this;
    }

    public ImageSpecBuilder author(String author) {

        if (StringUtils.isNotBlank(author)) {
            log.debug("author {}", author);
            this.specification = CriteriaQueryUtils.and(this.specification,
                    (root, query, cb) -> {
                        Join<Image, Author> authorJoin = root.join(Image_.author);
                        return cb.like(cb.lower(authorJoin.get(Author_.name)), CriteriaQueryUtils.toLikePattern(author));
                    });
        }

        return this;
    }

    public ImageSpecBuilder orAuthor(String author) {

        if (StringUtils.isNotBlank(author)) {
            this.specification = CriteriaQueryUtils.or(this.specification,
                    (root, query, cb) -> {
                        Join<Image, Author> authorJoin = root.join(Image_.author);
                        return cb.like(cb.lower(authorJoin.get(Author_.name)), CriteriaQueryUtils.toLikePattern(author));
                    });
        }

        return this;
    }

    public ImageSpecBuilder orDate(String date) {

        if (date.matches(DATE_REGEX)) {
            this.specification = CriteriaQueryUtils.or(this.specification,
                    (root, query, cb) ->
                            cb.equal(root.get(Image_.date), LocalDate.parse(date))
            );
        }

        return this;
    }

    public ImageSpecBuilder dateFrom(LocalDate dateFrom) {
        if (dateFrom != null) {
            log.debug("dateFrom {}", dateFrom);
            this.specification = CriteriaQueryUtils.and(this.specification,
                    (root, query, cb) ->
                            cb.greaterThanOrEqualTo(root.get(Image_.date), dateFrom)
            );
        }
        return this;
    }

    public ImageSpecBuilder dateTo(LocalDate dateTo) {
        if (dateTo != null) {
            log.debug("dateTo {}", dateTo);
            this.specification = CriteriaQueryUtils.and(this.specification,
                    (root, query, cb) ->
                            cb.lessThanOrEqualTo(root.get(Image_.date), dateTo)
            );
        }
        return this;
    }

    public ImageSpecBuilder tagsIn(Set<String> tags) {

        if (tags != null && !tags.isEmpty()) {
            log.debug("tags {}", tags);
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
