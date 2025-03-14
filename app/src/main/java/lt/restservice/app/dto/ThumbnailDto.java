package lt.restservice.app.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ThumbnailDto {

    private final Long id;
    private final String imageName;
    private final String description;
    private final String authorName;
    private final Date date;
    private final byte[] thumbnail;

}
