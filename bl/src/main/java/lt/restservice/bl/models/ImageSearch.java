package lt.restservice.bl.models;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ImageSearch {
    private final int pageNumber;
    private final int pageSize;
    private final String query;
    private final String imageName;
    private final String description;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String authorName;
    private final Set<String> tagNames;

    public Pageable getPageable() {
        return PageRequest.of(pageNumber, pageSize);
    }
}