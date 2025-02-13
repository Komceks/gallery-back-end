package lt.restservice.bl;

import lt.restservice.model.Image;
import lt.restservice.model.Tag;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final TagService tagService;

    @Transactional
    public void uploadImage(ImageUploadDto dto) {
        try {
            Image image = ImageUploadMapper.toImage(dto);
            Set<Tag> tags = new java.util.HashSet<>();

            if (!dto.getTags().isEmpty()) {

                for (String tagName : dto.getTags()) {

                    Tag tag = tagService.findOrCreateTag(tagName);
                    tags.add(tag);
                    System.out.println("Tag created: " + tag.getName());
                }

                image.setTags(tags);
            }

            System.out.println("Saving image: " + image.getName());
            imageRepository.save(image);

        } catch (ConstraintViolationException ex) {

            System.err.println("Constraint violation during image save:");
            throw ex;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }
    }
}
