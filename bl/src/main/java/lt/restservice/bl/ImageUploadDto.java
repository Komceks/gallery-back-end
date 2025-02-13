package lt.restservice.bl;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class ImageUploadDto {

    private final String name;
    private final String description;
    private final String author;
    private final Date date;
    private final byte[] image;
    private final List<String> tags;
}
