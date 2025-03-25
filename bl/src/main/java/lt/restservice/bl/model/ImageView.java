package lt.restservice.bl.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ImageView {
    private final byte[] image;
    private final Set<String> tags;
}