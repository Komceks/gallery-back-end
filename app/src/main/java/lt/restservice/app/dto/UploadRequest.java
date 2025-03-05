package lt.restservice.app.dto;

import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadRequest {

    private final String imageName;
    private final String description;
    private final String authorName;
    private final Date date;
    private final Set<String> tags;

}
