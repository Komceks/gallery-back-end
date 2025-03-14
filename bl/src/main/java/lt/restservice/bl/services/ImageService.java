package lt.restservice.bl.services;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import lt.restservice.bl.models.ImageModel;
import lt.restservice.bl.utils.ThumbnailGenerator;
import lt.restservice.bl.repositories.ImageRepository;
import lt.restservice.model.Author;
import lt.restservice.model.Image;
import lt.restservice.model.Tag;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public void uploadImage(ImageModel imageModel) throws IOException {
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

    @Transactional
    public Page<ImageModel> getImagePage(int page, int size) {

        Page<Image> imagePage = imageRepository.findAll(PageRequest.of(page, size));

        return buildImageModelPage(imagePage);
    }

    @Transactional
    public Page<ImageModel> getImagePage(int page, int size, String query) {

        Page<Image> imagePage = imageRepository.findBySingleQuery(PageRequest.of(page, size), query);

        return buildImageModelPage(imagePage);
    }

    @Transactional
    public Page<ImageModel> getImagePage(int page, int size, ImageModel imageModel) {

        Page<Image> imagePage = imageRepository.findByImageModel(PageRequest.of(page, size), imageModel);

        return buildImageModelPage(imagePage);
    }

    @Transactional
    protected Page<ImageModel> buildImageModelPage(Page<Image> imagePage) {

        return imagePage.map(image ->
                ImageModel.builder()
                        .id(image.getId())
                        .imageFile(image.getImageBlob())
                        .imageName(image.getName())
                        .description(image.getDescription())
                        .date(image.getDate())
                        .authorName(image.getAuthor().getName())
                        .tagNames(image.getTags().stream()
                                .map(Tag::getName)
                                .collect(Collectors.toSet()))
                        .uploadDate(image.getUploadDate())
                        .thumbnail(image.getThumbnail())
                        .build());
    }
}
