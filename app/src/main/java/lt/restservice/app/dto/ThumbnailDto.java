package lt.restservice.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lt.restservice.bl.models.ThumbnailDtoList;

@Data
@AllArgsConstructor
@Builder
public class ThumbnailDto {
    private final Long id;
    private final String imageName;
    private final String description;
    private final String authorName;
    private final LocalDate date;
    private final byte[] thumbnail;

    public static ThumbnailDto of(ThumbnailDtoList thumbnailDtoList) {
        return ThumbnailDto.builder()
                .id(thumbnailDtoList.getId())
                .imageName(thumbnailDtoList.getImageName())
                .description(thumbnailDtoList.getDescription())
                .authorName(thumbnailDtoList.getAuthorName())
                .date(thumbnailDtoList.getDate())
                .thumbnail(thumbnailDtoList.getThumbnail())
                .build();
    }
}
