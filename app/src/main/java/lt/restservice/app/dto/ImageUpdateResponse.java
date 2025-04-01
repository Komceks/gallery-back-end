package lt.restservice.app.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lt.restservice.bl.model.ImageUpdateModel;

@Data
@AllArgsConstructor
@Builder
public class ImageUpdateResponse {
    private final Long id;
    private final String imageName;
    private final String description;
    private final String authorName;
    private final LocalDate date;
    private final Set<String> tags;
    private final byte[] image;

    public static ImageUpdateResponse of(ImageUpdateModel model) {
        return ImageUpdateResponse.builder()
                .id(model.getId())
                .imageName(model.getImageName())
                .description(model.getDescription())
                .authorName(model.getAuthorName())
                .date(model.getDate())
                .tags(model.getTagNames())
                .image(model.getImageFile())
                .build();
    }
}