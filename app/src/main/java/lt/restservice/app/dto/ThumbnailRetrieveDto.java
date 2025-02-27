package lt.restservice.app.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThumbnailRetrieveDto {

    private final String imageName;
    private final String description;
    private final String authorName;
    private final Date date;
    private final Set<String> tags;
    private final Timestamp uploadDate;
    private final byte[] thumbnail;

}
