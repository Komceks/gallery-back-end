package lt.restservice.bl;

import lt.restservice.model.Author;
import lt.restservice.model.Image;
import lt.restservice.model.Tag;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final TagService tagService;
    private final AuthorService authorService;

    @Transactional
    public void uploadImage(ImageUploadDto dto) throws IOException {
        try {

            Author author = authorService.findOrCreateAuthor(dto.getAuthorName());

            Image image = ImageUploadMapper.toImage(dto, author);

            Set<Tag> tags = new HashSet<>();

            if (!dto.getTags().isEmpty()) {

                for (String tagName : dto.getTags()) {

                    Tag tag = tagService.findOrCreateTag(tagName, image);
                    tags.add(tag);

                    log.debug("Tag created: {}", tag.getName());
                }

                image.getTags().addAll(tags);
            }

            log.debug("Saving image: {}", image.getName());
            imageRepository.save(image);

        } catch (ConstraintViolationException ex) {

            log.error("Constraint violation during image save:");
            throw ex;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }
    }
}
