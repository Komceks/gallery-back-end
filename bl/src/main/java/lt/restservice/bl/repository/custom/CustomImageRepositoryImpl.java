package lt.restservice.bl.repository.custom;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.Join;

import jakarta.persistence.criteria.Path;

import lt.restservice.bl.model.ImageSearchModel;
import lt.restservice.bl.model.ThumbnailListModel;
import lt.restservice.bl.repository.TagRepository;
import lt.restservice.bl.repository.specification.ImageSpecification;
import lt.restservice.bl.model.ImageViewModel;
import lt.restservice.bl.sort.SortEnum;
import lt.restservice.model.Author;
import lt.restservice.model.Author_;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CustomImageRepositoryImpl implements CustomImageRepository {

    private final EntityManager em;
    private final TagRepository tagRepository;

    public Page<ThumbnailListModel> search(ImageSearchModel imageSearchModel) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Image> image = cq.from(Image.class);

        Join<Image, Author> authorJoin = image.join(Image_.author);
        Path<Long> idPath = image.get(Image_.id);
        Path<String> namePath = image.get(Image_.name);
        Path<String> descriptionPath = image.get(Image_.description);
        Path<LocalDate> datePath = image.get(Image_.date);
        Path<String> authorNamePath = authorJoin.get(Author_.name);
        Path<byte[]> thumbnailPath = image.get(Image_.thumbnail);
        Path<LocalDateTime> uploadDatePath = image.get(Image_.uploadDate);

        cq.multiselect(
                idPath,
                namePath,
                descriptionPath,
                datePath,
                authorNamePath,
                thumbnailPath,
                uploadDatePath
        );

        Specification<Image> searchSpecification = ImageSpecification.buildSpecification(imageSearchModel);
        Predicate predicate = searchSpecification.toPredicate(image, cq, cb);
        Long searchCount = countBySearch(searchSpecification);

        cq.distinct(true)
                .where(predicate)
                .orderBy(SortEnum.buildOrder(image, cb, imageSearchModel.getSort().getField(), imageSearchModel.getSort().getOrder()));

        Pageable pageable = imageSearchModel.getPageable();

        List<Tuple> resultList = em.createQuery(cq)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<Long> imageIds = resultList.stream()
                .map(tuple -> tuple.get(idPath))
                .toList();

        Map<Long, Set<String>> imageTags = tagRepository.findByImageIds(imageIds);

        List<ThumbnailListModel> result = resultList.stream()
                .map(tuple -> {
                    ThumbnailListModel.ThumbnailListModelBuilder builder = ThumbnailListModel.builder()
                            .id(tuple.get(idPath))
                            .imageName(tuple.get(namePath))
                            .description(tuple.get(descriptionPath))
                            .date(tuple.get(datePath))
                            .authorName(tuple.get(authorNamePath))
                            .thumbnail(tuple.get(thumbnailPath))
                            .uploadDate(tuple.get(uploadDatePath));

                    if (imageTags.containsKey(tuple.get(idPath))) {
                        builder.tags(imageTags.get(tuple.get(idPath)));
                    }

                    return builder.build();
                })
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

    public ImageViewModel view(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);
        Root<Image> imageRoot = cq.from(Image.class);

        Specification<Image> specification = ImageSpecification.buildSpecification(id);
        Predicate predicate = specification.toPredicate(imageRoot, cq, cb);

        cq.select(imageRoot)
                .distinct(true)
                .where(predicate);

        Image image = em.createQuery(cq).getSingleResult();
        Set<String> tagNames = tagRepository.findByImageId(id);

        return ImageViewModel.builder()
                .image(image.getImageBlob())
                .tags(tagNames)
                .build();
    }

}
