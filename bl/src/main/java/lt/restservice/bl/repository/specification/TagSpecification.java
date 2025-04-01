package lt.restservice.bl.repository.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import lt.restservice.bl.utils.CriteriaQueryUtils;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;
import lt.restservice.model.Tag;
import lt.restservice.model.Tag_;

public class TagSpecification {

    public static Specification<Tag> buildSpecification(Long id) {
        return TagSpecification.TagSpecBuilder.builder()
                .id(id)
                .build();
    }

    public static Specification<Tag> buildSpecification(List<Long> id) {
        return TagSpecification.TagSpecBuilder.builder()
                .inIds(id)
                .build();
    }

    private static class TagSpecBuilder {
        private Specification<Tag> specification;

        private TagSpecBuilder() {
            this.specification = null;
        }

        public static TagSpecBuilder builder() {
            return new TagSpecBuilder();
        }

        public Specification<Tag> build() {
            return this.specification == null ?
                    (root, query, cb) -> cb.conjunction() : this.specification;
        }

        public TagSpecBuilder id(Long id) {
            if (id != null) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) -> {
                            Join<Tag, Image> imageJoin = root.join(Tag_.images);
                            return cb.equal(imageJoin.get(Image_.id), id);
                        });
            }

            return this;
        }

        public TagSpecBuilder inIds(List<Long> ids) {
            if (!ids.isEmpty()) {
                this.specification = CriteriaQueryUtils.and(this.specification,
                        (root, query, cb) -> {
                            Join<Tag, Image> imageJoin = root.join(Tag_.images);
                            return imageJoin.get(Image_.id).in(ids);
                        });
            }

            return this;
        }
    }
}
