package lt.restservice.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lt.restservice.bl.models.ThumbnailListDto;

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

    public static ThumbnailDto of(ThumbnailListDto thumbnailListDto) {
        return ThumbnailDto.builder()
                .id(thumbnailListDto.getId())
                .imageName(thumbnailListDto.getImageName())
                .description(thumbnailListDto.getDescription())
                .authorName(thumbnailListDto.getAuthorName())
                .date(thumbnailListDto.getDate())
                .thumbnail(thumbnailListDto.getThumbnail())
                .build();
    }
}
