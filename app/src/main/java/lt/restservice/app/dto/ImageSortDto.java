package lt.restservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lt.restservice.bl.sort.SortEnum;
import lt.restservice.bl.sort.SortOrder;
import lt.restservice.model.Image;

@AllArgsConstructor
@Data
public class ImageSortDto<T extends SortEnum<Image>> {
    private final T field;
    private final SortOrder order;
}
