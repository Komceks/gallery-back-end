package lt.restservice.bl.sort;

import java.util.function.Supplier;

import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lt.restservice.model.Image;
import lt.restservice.model.Image_;

@AllArgsConstructor
@Getter
public enum ImageSortEnum implements SortEnum<Image> {
    UPLOAD_DATE(() -> Image_.uploadDate),
    NAME(() -> Image_.name),
    DATE(() -> Image_.date);

    private final Supplier<SingularAttribute<Image, ?>> sortAttributeSupplier;
}
