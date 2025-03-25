package lt.restservice.app.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lt.restservice.bl.model.ImageView;

@Data
@AllArgsConstructor
@Builder
public class ImageViewResponse {
    private final byte[] image;
    private final Set<String> tags;

    public static ImageViewResponse of(ImageView imageView) {
        return ImageViewResponse.builder()
                .image(imageView.getImage())
                .tags(imageView.getTags())
                .build();
    }
}
