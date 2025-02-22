package lt.restservice.bl;

import java.io.IOException;

import lt.restservice.model.Author;
import lt.restservice.model.Image;

public class ImageUploadMapper {

    public static Image toImage(ImageUploadDto dto, Author author) throws IOException {

        return new Image(dto.getImageFile().getBytes(), dto.getImageName(), dto.getDescription(), dto.getDate(), author);
    }
}
