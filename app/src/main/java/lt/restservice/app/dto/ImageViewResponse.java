package lt.restservice.app.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lt.restservice.bl.model.ImageViewModel;

@Data
@AllArgsConstructor
@Builder
public class ImageViewResponse {
    private final byte[] image;
    private final Set<String> tags;

    public static ImageViewResponse of(ImageViewModel imageViewModel) {
        return ImageViewResponse.builder()
                .image(imageViewModel.getImage())
                .tags(imageViewModel.getTags())
                .build();
    }
}
