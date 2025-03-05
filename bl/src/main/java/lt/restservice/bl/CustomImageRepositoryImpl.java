package lt.restservice.bl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.common.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.model.Image;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomImageRepositoryImpl implements CustomImageRepository {

    private final EntityManager em;

    @Transactional(readOnly = true)
    public List<Image> searchImages(int startIdx, int endIdx, String query) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);
        Root<Image> image = cq.from(Image.class);

        if (StringUtils.isBlank(query)) {
            cq.select(image);
            return em.createQuery(cq).setFirstResult(startIdx * endIdx).setMaxResults(endIdx).getResultList();
        }

        image.fetch("tags", JoinType.LEFT);
        image.fetch("author", JoinType.LEFT);

        String[] tokens = query.toLowerCase().split("\\s+");
        List<Predicate> tokenPredicates = new ArrayList<>();

        for (String token : tokens) {

            String likePattern = "%" + token + "%";

            Predicate imageNamePredicate = cb.like(cb.lower(image.get("name")), likePattern);
            Predicate authorNamePredicate = cb.like(cb.lower(image.get("author").get("name")), likePattern);
            Predicate descriptionPredicate = cb.like(cb.lower(image.get("description")), likePattern);

            Predicate tagsPredicate = cb.like(cb.lower(image.get("tags").get("name")), likePattern);

            if (token.matches("^\\d{4}-\\d{2}-\\d{2}$")) {

                try {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    Predicate datePredicate = cb.equal(image.get("date").as(java.sql.Date.class),
                            new java.sql.Date(dateFormat.parse(token).getTime()));

                    tokenPredicates.add(cb.or(imageNamePredicate, authorNamePredicate, descriptionPredicate,
                            datePredicate, tagsPredicate));

                } catch (ParseException e) {

                    log.debug("Date parse exception: {}", e.getMessage());
                }

            } else {

                tokenPredicates.add(cb.or(imageNamePredicate, authorNamePredicate, descriptionPredicate, tagsPredicate));
            }
        }
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.and(tokenPredicates.toArray(new Predicate[0])));
        cq.select(image).where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).setFirstResult(startIdx * endIdx).setMaxResults(endIdx).getResultList();
    }
}
