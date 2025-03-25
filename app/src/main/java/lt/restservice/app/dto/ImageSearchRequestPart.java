package lt.restservice.app.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ImageSearchRequestPart {
    private final String imageName;
    private final String description;
    private final String authorName;
    @PastOrPresent
    private final LocalDate dateFrom;
    @PastOrPresent
    private final LocalDate dateTo;
    private final Set<String> tags;
}
