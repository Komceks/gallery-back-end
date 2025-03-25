package lt.restservice.bl.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@Builder
public class ThumbnailListModel {
    private final Long id;
    private final String imageName;
    private final String description;
    private final String authorName;
    private final LocalDate date;
    private final byte[] thumbnail;
}
