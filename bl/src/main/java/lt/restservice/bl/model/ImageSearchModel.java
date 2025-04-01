package lt.restservice.bl.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ImageSearchModel {
    private final int pageNumber;
    private final int pageSize;
    private final ImageSortModel sort;

    private final String query;
    private final ImageSearchPartModel imageSearchPartModel;

    public Pageable getPageable() {
        return PageRequest.of(pageNumber, pageSize);
    }
}