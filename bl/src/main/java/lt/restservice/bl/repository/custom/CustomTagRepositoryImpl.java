package lt.restservice.bl.repository.custom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lt.restservice.bl.repository.specification.TagSpecification;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;
import lt.restservice.model.Tag;
import lt.restservice.model.Tag_;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomTagRepositoryImpl implements CustomTagRepository {

    private final EntityManager em;

    public Set<String> findByImageId(Long imageId) {
        if (imageId == null) {
            return new HashSet<>();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Tag> tag = cq.from(Tag.class);

        Specification<Tag> specification = TagSpecification.buildSpecification(imageId);
        Predicate predicate = specification.toPredicate(tag, cq, cb);

        cq.select(tag.get(Tag_.name))
                .distinct(true)
                .where(predicate);

        return em.createQuery(cq).getResultStream().collect(Collectors.toSet());
    }

    public Map<Long, Set<String>> findByImageIds(List<Long> imageIds) {
        if (imageIds == null || imageIds.isEmpty()) {
            return new HashMap<>();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Tag> tag = cq.from(Tag.class);

        Join<Tag, Image> imageJoin = tag.join(Tag_.images);
        Path<Long> imageIdPath = imageJoin.get(Image_.id);
        Path<String> tagNamePath = tag.get(Tag_.name);

        Specification<Tag> specification = TagSpecification.buildSpecification(imageIds);
        Predicate predicate = specification.toPredicate(tag, cq, cb);

        cq.multiselect(imageIdPath, tagNamePath).where(predicate);
        List<Tuple> resultList = em.createQuery(cq).getResultList();

        return resultList.stream()
                .collect(
                        Collectors.groupingBy(tuple -> tuple.get(imageIdPath),
                                Collectors.mapping(
                                        tuple -> tuple.get(tagNamePath),
                                        Collectors.toSet()
                                )
                        )
                );
    }
}
