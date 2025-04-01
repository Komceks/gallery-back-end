package lt.restservice.bl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lt.restservice.bl.sort.SortEnum;
import lt.restservice.bl.sort.SortOrder;
import lt.restservice.model.Image;

@AllArgsConstructor
@Data
@Builder
public class ImageSortModel {
    private final SortEnum<Image> field;
    private final SortOrder order;
}
