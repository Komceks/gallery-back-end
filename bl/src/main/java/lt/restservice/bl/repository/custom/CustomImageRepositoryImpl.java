package lt.restservice.bl.repository.custom;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.Join;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.SetJoin;

import lt.restservice.bl.model.ImageSearch;
import lt.restservice.bl.model.ThumbnailListModel;
import lt.restservice.bl.repository.specification.ImageSpecification;
import lt.restservice.bl.model.ImageView;
import lt.restservice.model.Author;
import lt.restservice.model.Author_;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;
import lt.restservice.model.Tag;
import lt.restservice.model.Tag_;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomImageRepositoryImpl implements CustomImageRepository {

    private final EntityManager em;

    public Page<ThumbnailListModel> search(ImageSearch imageSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Image> image = cq.from(Image.class);

        Specification<Image> searchSpecification = ImageSpecification.buildSpecification(imageSearch);
        Predicate predicate = searchSpecification.toPredicate(image, cq, cb);
        Long searchCount = countBySearch(searchSpecification);
        cq.distinct(true).where(predicate);

        Join<Image, Author> authorJoin = image.join(Image_.author);
        Path<Long> idPath = image.get(Image_.id);
        Path<String> namePath = image.get(Image_.name);
        Path<String> descriptionPath = image.get(Image_.description);
        Path<LocalDate> datePath = image.get(Image_.date);
        Path<String> authorNamePath = authorJoin.get(Author_.name);
        Path<byte[]> thumbnailPath = image.get(Image_.thumbnail);

        cq.multiselect(
                idPath,
                namePath,
                descriptionPath,
                datePath,
                authorNamePath,
                thumbnailPath
        );

        Pageable pageable = imageSearch.getPageable();
        List<Tuple> resultList = em.createQuery(cq)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<ThumbnailListModel> result = resultList.stream()
                .map(tuple -> ThumbnailListModel.builder()
                        .id(tuple.get(idPath))
                        .imageName(tuple.get(namePath))
                        .description(tuple.get(descriptionPath))
                        .date(tuple.get(datePath))
                        .authorName(tuple.get(authorNamePath))
                        .thumbnail(tuple.get(thumbnailPath))
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, searchCount);
    }

    private Long countBySearch(Specification<Image> specification) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Image> image = cq.from(Image.class);
        Predicate predicate = specification.toPredicate(image, cq, cb);
        cq.select(cb.countDistinct(image));

        if (predicate != null) {
            cq.select(cb.countDistinct(image)).where(predicate);
        }

        return em.createQuery(cq).getSingleResult();
    }

    public ImageView viewImage(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Image> image = cq.from(Image.class);
        SetJoin<Image, Tag> tagJoin = image.join(Image_.tags, JoinType.LEFT);

        Path<byte[]> imageBlobPath = image.get(Image_.imageBlob);
        Path<String> tagNamePath = tagJoin.get(Tag_.name);

        cq.multiselect(
                imageBlobPath,
                tagNamePath
        );
        cq.where(cb.equal(image.get(Image_.id), id));

        List<Tuple> tuple = em.createQuery(cq).getResultList();

        return ImageView.builder()
                .image(tuple.getFirst().get(imageBlobPath))
                .tags(tuple.stream()
                        .map(tpl -> tpl.get(tagNamePath))
                        .collect(Collectors.toSet()))
                .build();
    }
}
