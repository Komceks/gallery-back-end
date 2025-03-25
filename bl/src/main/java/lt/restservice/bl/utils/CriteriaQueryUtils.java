package lt.restservice.bl.utils;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;

public class CriteriaQueryUtils {

    public static Predicate buildLikePatternPredicate(Expression<String> expression, String term, CriteriaBuilder cb) {
        return cb.like(cb.lower(expression), '%' + term.toLowerCase() + '%');
    }

    public static <T> Specification<T> or(Specification<T> specification, Specification<T> newSpecification) {
        return specification == null ? newSpecification : specification.or(newSpecification);
    }

    public static <T> Specification<T> and(Specification<T> specification, Specification<T> newSpecification) {
        return specification == null ? newSpecification : specification.and(newSpecification);
    }
}
