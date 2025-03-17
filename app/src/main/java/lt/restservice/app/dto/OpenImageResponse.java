package lt.restservice.app.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OpenImageResponse {
    private final byte[] image;
    private final Set<String> tags;
}
