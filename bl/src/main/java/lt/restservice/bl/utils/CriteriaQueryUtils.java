package lt.restservice.bl.utils;

import org.springframework.data.jpa.domain.Specification;

public class CriteriaQueryUtils {

    public static String toLikePattern(String token) {
        return "%" + token.toLowerCase() + "%";
    }

    public static <T> Specification<T> or(Specification<T> specification, Specification<T> newSpecification) {
        return specification == null ? newSpecification : specification.or(newSpecification);
    }

    public static <T> Specification<T> and(Specification<T> specification, Specification<T> newSpecification) {
        return specification == null ? newSpecification : specification.and(newSpecification);
    }
}
