package lt.restservice.bl.models;

import java.time.LocalDate;

import org.hibernate.sql.results.internal.TupleElementImpl;

import jakarta.persistence.Tuple;

import jakarta.persistence.TupleElement;
import jakarta.persistence.criteria.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;
import lt.restservice.model.Image_;

@Slf4j
@Data
@AllArgsConstructor
@Builder
public class ThumbnailDtoList {
    private final Long id;
    private final String imageName;
    private final String description;
    private final String authorName;
    private final LocalDate date;
    private final byte[] thumbnail;
}
