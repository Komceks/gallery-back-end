package lt.restservice.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lt.restservice.model.Author;
import lt.restservice.model.Tag;

@Data
@AllArgsConstructor
public class ImageCreateReq {

    private final byte[] imageFile;
    private final String imageName;
    private final String description;
    private final Date date;
    private final Author author;
    private final Set<Tag> tags;
    private final Timestamp uploadDate;
    private final byte[] thumbnail;
}
