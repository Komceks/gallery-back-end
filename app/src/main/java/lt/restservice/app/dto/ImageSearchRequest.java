package lt.restservice.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lt.restservice.bl.model.ImageSearchModel;
import lt.restservice.bl.model.ImageSearchPartModel;
import lt.restservice.bl.model.ImageSortModel;
import lt.restservice.bl.sort.ImageSortEnum;

@Data
@AllArgsConstructor
public class ImageSearchRequest {
    @PositiveOrZero
    private final int pageNumber;
    @Positive
    private final int pageSize;
    private final String query;
    @Valid
    private final ImageSearchRequestPart imageSearchRequestPart;
    private final ImageSortDto<ImageSortEnum> sort;

    @JsonIgnore
    @AssertTrue(message = "Either query or imageSearchRequestPart should be set")
    public boolean isValid() {
        return query == null ^ imageSearchRequestPart == null;
    }

    public ImageSearchModel toImageSearch() {
        ImageSearchModel.ImageSearchModelBuilder builder = ImageSearchModel.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .query(query)
                .sort(buildImageSortModel());

        if (imageSearchRequestPart != null) {
            ImageSearchPartModel imageSearchPartModel = ImageSearchPartModel.builder()
                    .imageName(imageSearchRequestPart.getImageName())
                    .description(imageSearchRequestPart.getDescription())
                    .dateFrom(imageSearchRequestPart.getDateFrom())
                    .dateTo(imageSearchRequestPart.getDateTo())
                    .authorName(imageSearchRequestPart.getAuthorName())
                    .tagNames(imageSearchRequestPart.getTags())
                    .build();

            builder = builder.imageSearchPartModel(imageSearchPartModel);
        }

        return builder.build();
    }

    private ImageSortModel buildImageSortModel() {
        return ImageSortModel.builder()
                .field(sort.getField())
                .order(sort.getOrder())
                .build();
    }
}
