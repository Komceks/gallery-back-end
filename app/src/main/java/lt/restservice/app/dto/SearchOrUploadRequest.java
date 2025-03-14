package lt.restservice.app.dto;

import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SearchOrUploadRequest {

    private final String imageName;
    private final String description;
    private final String authorName;
    private final Date date;
    private final Set<String> tags;

}
