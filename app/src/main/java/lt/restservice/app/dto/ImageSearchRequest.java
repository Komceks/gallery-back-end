package lt.restservice.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageSearchRequest {
    @PositiveOrZero
    private final int pageNumber;
    @Positive
    private final int pageSize;
    private final String query;
    @Valid
    private final ImageSearchRequestPart imageSearchRequestPart;

    @JsonIgnore
    @AssertTrue(message = "Either query or imageSearchRequestPart should be set")
    public boolean isValid() {
        return query == null ^ imageSearchRequestPart == null;
    }
}
