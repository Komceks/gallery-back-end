package lt.restservice.bl.repositories;

import static io.micrometer.common.util.StringUtils.isBlank;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.bl.models.ImageModel;
import lt.restservice.model.Author;
import lt.restservice.model.Author_;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;
import lt.restservice.model.Tag;
import lt.restservice.model.Tag_;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomImageRepositoryImpl implements CustomImageRepository {

    protected interface Countable {

        Long getCount();
    }

    @AllArgsConstructor
    protected class Query implements Countable {

        private final String query;

        @Override
        @Transactional
        public Long getCount() {

            Function<CriteriaQueryFromImage<?>, Predicate[]> predicateArrayFunction = CustomImageRepositoryImpl.this.getPredicateArrayFunction(this.query);
            return CustomImageRepositoryImpl.this.getCount(predicateArrayFunction);
        }
    }

    @AllArgsConstructor
    protected class Model implements Countable {

        private final ImageModel imageModel;

        @Override
        @Transactional
        public Long getCount() {

            Function<CriteriaQueryFromImage<?>, Predicate[]> predicateArrayFunction = CustomImageRepositoryImpl.this.getPredicateArrayFunction(this.imageModel);
            return CustomImageRepositoryImpl.this.getCount(predicateArrayFunction);
        }
    }

    @Getter
    public class CriteriaQueryFromImage<T> {

        private final CriteriaBuilder cb;
        private final CriteriaQuery<T> cq;
        private final Root<Image> image;

        CriteriaQueryFromImage(Class<T> queryType) {

            this.cb = em.getCriteriaBuilder();
            this.cq = cb.createQuery(queryType);
            this.image = cq.from(Image.class);
        }
    }

    private final EntityManager em;

    @Transactional(readOnly = true)
    public Page<Image> findBySingleQuery(PageRequest pageRequest, String query) {

        Query queryObj = new Query(query);
        CriteriaQueryFromImage<Image> criteria = new CriteriaQueryFromImage<>(Image.class);

        Predicate[] predicates = getPredicateArray(query, criteria.getCb(), criteria.getImage());

        return buildImagePage(predicates, criteria, pageRequest, queryObj);
    }

    @Transactional(readOnly = true)
    public Page<Image> findByImageModel(PageRequest pageRequest, ImageModel imageModel) {

        Model imageModelObj = new Model(imageModel);
        CriteriaQueryFromImage<Image> criteria = new CriteriaQueryFromImage<>(Image.class);

        Predicate[] predicates = getPredicateArray(imageModel, criteria.getCb(), criteria.getImage());

        return buildImagePage(predicates, criteria, pageRequest, imageModelObj);
    }

    @Transactional(readOnly = true)
    protected Predicate[] getPredicateArray(String query, CriteriaBuilder cb, Root<Image> image) {

        if (isBlank(query)) {
            return new Predicate[0];
        }

        List<Predicate> predicates = new ArrayList<>();

        Join<Image, Author> author = image.join(Image_.author);
        Join<Image, Tag> tags = image.join(Image_.tags);

        String[] tokens = query.toLowerCase().split("\\s+");
        List<Predicate> allTokenPredicates = new ArrayList<>();

        for (String token : tokens) {

            List<Predicate> tokenPredicates = new ArrayList<>();

            tokenPredicates = addLikePredicate(tokenPredicates, cb, token, image.get(Image_.name));
            tokenPredicates = addLikePredicate(tokenPredicates, cb, token, author.get(Author_.name));
            tokenPredicates = addLikePredicate(tokenPredicates, cb, token, image.get(Image_.description));
            tokenPredicates = addLikePredicate(tokenPredicates, cb, token, tags.get(Tag_.name));

            if (token.matches("^\\d{4}-\\d{2}-\\d{2}$")) {

                tokenPredicates.add(cb.equal(image.get(Image_.date), LocalDateTime.parse(token)));
            }

            allTokenPredicates.add(cb.or(tokenPredicates.toArray(new Predicate[0])));
        }

        predicates.add(cb.and(allTokenPredicates.toArray(new Predicate[0])));

        return predicates.toArray(new Predicate[0]);
    }

    @Transactional(readOnly = true)
    protected Predicate[] getPredicateArray(ImageModel imageModel, CriteriaBuilder cb, Root<Image> image) {

        List<Predicate> predicates = new ArrayList<>();

        predicates = addLikePredicate(predicates, cb, imageModel.getImageName(), image.get(Image_.name));
        predicates = addLikePredicate(predicates, cb, imageModel.getDescription(), image.get(Image_.description));

        Join<Image, Author> author = image.join(Image_.author);
        predicates = addLikePredicate(predicates, cb, imageModel.getAuthorName(), author.get(Author_.name));

        if (imageModel.getDate() != null) {

            predicates.add(cb.equal(image.get(Image_.date), imageModel.getDate()));
        }

        if (isNotEmpty(imageModel.getTagNames())) {

            Join<Image, Tag> tags = image.join(Image_.tags);
            predicates.add(tags.get(Tag_.name).in(imageModel.getTagNames()));
        }

        return predicates.isEmpty() ? new Predicate[0] : predicates.toArray(new Predicate[0]);
    }

    @Transactional(readOnly = true)
    protected Page<Image> buildImagePage(Predicate[] predicates, CriteriaQueryFromImage<Image> criteria, PageRequest pageRequest, Countable queryOrImageModel) {

        if (predicates.length == 0) {

            criteria.getCq().select(criteria.getImage());

            List<Image> imageList = em.createQuery(criteria.getCq()).setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
                    .setMaxResults(pageRequest.getPageSize()).getResultList();

            Long searchCount = queryOrImageModel.getCount();

            return new PageImpl<>(imageList, pageRequest, searchCount);
        }

        criteria.getCq().select(criteria.getImage()).where(criteria.getCb().and(predicates));

        List<Image> imageList = em.createQuery(criteria.getCq()).setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
                .setMaxResults(pageRequest.getPageSize()).getResultList();

        Long searchCount = queryOrImageModel.getCount();

        return new PageImpl<>(imageList, pageRequest, searchCount);
    }

    @Transactional(readOnly = true)
    protected List<Predicate> addLikePredicate(List<Predicate> predicates, CriteriaBuilder cb, String token, Expression<String> expression) {

        if (!isBlank(token)) {

            predicates.add(cb.like(cb.lower(expression), toLikePattern(token)));
        }

        return predicates;
    }

    @Transactional(readOnly = true)
    protected String toLikePattern(String token) {

        return "%" + token.toLowerCase() + "%";
    }

    @Transactional(readOnly = true)
    protected Function<CriteriaQueryFromImage<?>, Predicate[]> getPredicateArrayFunction(String query) {

        return (criteria) -> getPredicateArray(query, criteria.getCb(), criteria.getImage());
    }

    @Transactional(readOnly = true)
    protected Function<CriteriaQueryFromImage<?>, Predicate[]> getPredicateArrayFunction(ImageModel imageModel) {

        return (criteria) -> getPredicateArray(imageModel, criteria.getCb(), criteria.getImage());
    }

    @Transactional(readOnly = true)
    protected Long getCount(Function<CriteriaQueryFromImage<?>, Predicate[]> getPredicateArrayFunction) {

        CriteriaQueryFromImage<Long> criteria = new CriteriaQueryFromImage<>(Long.class);
        Predicate[] predicates = getPredicateArrayFunction.apply(criteria);

        if (predicates.length == 0) {

            criteria.getCq().select(criteria.getCb().count(criteria.getImage()));

        } else {

            criteria.getCq().select(criteria.getCb().count(criteria.getImage())).where(predicates);
        }

        return em.createQuery(criteria.getCq()).getSingleResult();
    }
}
