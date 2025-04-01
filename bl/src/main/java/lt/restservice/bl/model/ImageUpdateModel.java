package lt.restservice.bl.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lt.restservice.model.Image;
import lt.restservice.model.Tag;

@Data
@AllArgsConstructor
@Builder
public class ImageUpdateModel {
    private final Long id;
    private final byte[] imageFile;
    private final String imageName;
    private final String description;
    private final LocalDate date;
    private final String authorName;
    private final Set<String> tagNames;
    private final LocalDateTime uploadDate;

    public static ImageUpdateModel of(Image image) {
        return ImageUpdateModel.builder()
                .id(image.getId())
                .imageFile(image.getImageBlob())
                .imageName(image.getName())
                .description(image.getDescription())
                .date(image.getDate())
                .authorName(image.getAuthor().getName())
                .tagNames(buildTagNameSet(image.getTags()))
                .uploadDate(image.getUploadDate())
                .build();
    }

    private static Set<String> buildTagNameSet(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }
}