package lt.restservice.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ImageSearchRequest {
    @PositiveOrZero
    private final int page;

    @PositiveOrZero
    private final int size;

    private final String query;

    @Valid
    private final ImageSearchDto imageSearchDto;

    @AssertTrue(message = "Either query or imageSearchDto should be set")
    public boolean isValid() {
        return query == null ^ imageSearchDto == null;
    }
}
