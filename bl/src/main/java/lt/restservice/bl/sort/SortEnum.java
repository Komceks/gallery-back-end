package lt.restservice.bl.sort;

import java.util.function.Supplier;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

public interface SortEnum<T> {
    Supplier<SingularAttribute<T, ?>> getSortAttributeSupplier();

    static <T> Order buildOrder(Root<T> root, CriteriaBuilder cb, SortEnum<T> sortEnum, SortOrder sortDirection) {
        SingularAttribute<T, ?> sortAttribute = sortEnum.getSortAttributeSupplier().get();
        Path<?> sortingPath = root.get(sortAttribute);

        if (sortDirection == SortOrder.DESC) {
            return cb.desc(sortingPath);
        }
        return cb.asc(sortingPath);
    }
}
