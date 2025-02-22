package lt.restservice.bl;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImageUploadDto {

    private final String imageName;
    private final String description;
    private final String authorName;
    private final Date date;
    private final MultipartFile imageFile;
    private final List<String> tags;

}
