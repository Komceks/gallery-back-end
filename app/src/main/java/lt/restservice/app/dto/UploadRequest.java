package lt.restservice.app.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// At this point its uploadRequest,
// since its just the upload requests from front-end,
// with MR3 it might change back to ImageDto
public class UploadRequest {

    private final String imageName;
    private final String description;
    private final String authorName;
    private final Date date;
    private final Set<String> tags;
    private final Timestamp uploadDate;

}
