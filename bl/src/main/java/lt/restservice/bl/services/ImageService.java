package lt.restservice.bl.services;

import java.io.IOException;
import java.util.Set;

import lt.restservice.bl.models.CreateImageModel;
import lt.restservice.bl.models.ImageView;
import lt.restservice.bl.models.ImageSearch;
import lt.restservice.bl.models.ThumbnailListDto;
import lt.restservice.bl.utils.ThumbnailGenerator;
import lt.restservice.bl.repositories.ImageRepository;
import lt.restservice.model.Author;
import lt.restservice.model.Image;
import lt.restservice.model.Tag;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final TagService tagService;
    private final AuthorService authorService;

    public void uploadImage(CreateImageModel createImageModel) throws IOException {
        try {
            Set<Tag> tagSet = tagService.findOrCreateTags(createImageModel.getTagNames());
            Author author = authorService.findOrCreateAuthor(createImageModel.getAuthorName());
            byte[] thumbnail = ThumbnailGenerator.createThumbnail(createImageModel.getImageFile());
            Image image = new Image();
            image.setImageBlob(createImageModel.getImageFile())
                    .setName(createImageModel.getImageName())
                    .setDescription(createImageModel.getDescription())
                    .setDate(createImageModel.getDate())
                    .setAuthor(author)
                    .setTags(tagSet)
                    .setUploadDate(createImageModel.getUploadDate())
                    .setThumbnail(thumbnail);

            log.debug("Saving image: {}", image);
            imageRepository.save(image);

        } catch (ConstraintViolationException ex) {
            log.error("Constraint violation during image save:");
            throw ex;
        }
    }

    public Page<ThumbnailListDto> createThumbnailListDtoPage(ImageSearch imageSearch) {
        return imageRepository.findByImageSearchRequest(imageSearch);
    }

    @Transactional
    public ImageView createImageViewById(Long id) {
        return imageRepository.findImageViewById(id);
    }
}
