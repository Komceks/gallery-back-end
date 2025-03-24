package lt.restservice.app.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageUploadRequest {
    @NotBlank
    private final String imageName;
    private final String description;
    @NotBlank
    private final String authorName;
    @PastOrPresent
    private final LocalDate date;
    private final Set<String> tags;
}
