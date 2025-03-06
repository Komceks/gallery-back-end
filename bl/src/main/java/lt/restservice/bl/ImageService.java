package lt.restservice.bl;

import java.io.IOException;
import java.util.Set;

import lt.restservice.model.Author;
import lt.restservice.model.Image;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import lt.restservice.model.Tag;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final TagService tagService;
    private final AuthorService authorService;

    @Transactional
    public void uploadImage(CreateImageModel imageModel) throws IOException {
        try {

            Set<Tag> tagSet = tagService.findOrCreateTags(imageModel.getTagNames());

            Author author = authorService.findOrCreateAuthor(imageModel.getAuthorName());

            byte[] thumbnail = ThumbnailGenerator.createThumbnail(imageModel.getImageFile());

            Image image = new Image();
            image.setImageBlob(imageModel.getImageFile())
                    .setName(imageModel.getImageName())
                    .setDescription(imageModel.getDescription())
                    .setDate(imageModel.getDate())
                    .setAuthor(author)
                    .setTags(tagSet)
                    .setUploadDate(imageModel.getUploadDate())
                    .setThumbnail(thumbnail);

            log.debug("Saving image: {}", image);

            imageRepository.save(image);

        } catch (ConstraintViolationException ex) {

            log.error("Constraint violation during image save:");
            throw ex;

        }
    }
}
