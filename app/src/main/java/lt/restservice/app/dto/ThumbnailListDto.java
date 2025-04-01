package lt.restservice.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lt.restservice.bl.model.ThumbnailListModel;

@Data
@AllArgsConstructor
@Builder
public class ThumbnailListDto {
    private final Long id;
    private final String imageName;
    private final String description;
    private final String authorName;
    private final LocalDate date;
    private final byte[] thumbnail;
    private final LocalDateTime uploadDate;
    private final Set<String> tags;

    public static ThumbnailListDto of(ThumbnailListModel thumbnailListModel) {
        return ThumbnailListDto.builder()
                .id(thumbnailListModel.getId())
                .imageName(thumbnailListModel.getImageName())
                .description(thumbnailListModel.getDescription())
                .authorName(thumbnailListModel.getAuthorName())
                .date(thumbnailListModel.getDate())
                .thumbnail(thumbnailListModel.getThumbnail())
                .tags(thumbnailListModel.getTags())
                .build();
    }
}
