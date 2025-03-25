package lt.restservice.bl.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateImageModel {
    private final Long id;
    private final byte[] imageFile;
    private final String imageName;
    private final String description;
    private final LocalDate date;
    private final String authorName;
    private final Set<String> tagNames;
    private final LocalDateTime uploadDate;
    private final byte[] thumbnail;
}