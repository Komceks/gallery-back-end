package lt.restservice.bl;

import lt.restservice.model.Image;
import lt.restservice.model.Tag;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ImageUploadMapper {

    public static ImageUploadDto toDto(Image image) {

        String name = image.getName();
        String description = image.getDescription();
        String author = image.getAuthor();
        Date date = image.getDate();
        byte[] image_bytes = image.getImage();
        List<String> tags = new java.util.ArrayList<>(List.of());

        for (Tag tag : image.getTags()) {

            tags.add(tag.getName());
        }

        return new ImageUploadDto(name, description, author, date, image_bytes, tags);
    }

    public static Image toImage(ImageUploadDto dto) {

        return new Image(dto.getImage(), dto.getName(), dto.getDescription(), dto.getAuthor(), dto.getDate());
    }
}
