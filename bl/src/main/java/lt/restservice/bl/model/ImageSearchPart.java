package lt.restservice.bl.model;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ImageSearchPart {
    private final String imageName;
    private final String description;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String authorName;
    private final Set<String> tagNames;
}
