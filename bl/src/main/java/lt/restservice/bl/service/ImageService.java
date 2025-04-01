package lt.restservice.bl.service;

import java.io.IOException;
import java.util.Set;

import lt.restservice.bl.model.CreateImageModel;
import lt.restservice.bl.model.ImageSearchModel;
import lt.restservice.bl.model.ImageUpdateModel;
import lt.restservice.bl.model.ThumbnailListModel;
import lt.restservice.bl.model.ImageViewModel;

import lt.restservice.bl.utils.ThumbnailGenerator;
import lt.restservice.bl.repository.ImageRepository;
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

    public void upload(CreateImageModel createImageModel) throws IOException {
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

            imageRepository.save(image);

        } catch (ConstraintViolationException ex) {
            log.error("Constraint violation during image save:", ex);
        }
    }

    public Page<ThumbnailListModel> search(ImageSearchModel imageSearchModel) {
        return imageRepository.search(imageSearchModel);
    }

    public ImageViewModel view(Long id) {
        return imageRepository.view(id);
    }

    public ImageUpdateModel update(ImageUpdateModel imageUpdateModel) throws IOException {

        Image image = imageRepository.findById(imageUpdateModel.getId()).orElse(null);

        if (image != null) {
            Set<Tag> tagSet = tagService.findOrCreateTags(imageUpdateModel.getTagNames());
            Author author = authorService.findOrCreateAuthor(imageUpdateModel.getAuthorName());
            image.setName(imageUpdateModel.getImageName())
                    .setDescription(imageUpdateModel.getDescription())
                    .setDate(imageUpdateModel.getDate())
                    .setAuthor(author)
                    .setTags(tagSet)
                    .setUploadDate(imageUpdateModel.getUploadDate());

            if (imageUpdateModel.getImageFile() != null) {
                byte[] thumbnail = ThumbnailGenerator.createThumbnail(imageUpdateModel.getImageFile());
                image.setImageBlob(imageUpdateModel.getImageFile())
                        .setThumbnail(thumbnail);
            }

            return ImageUpdateModel.of(imageRepository.save(image));
        }
        throw new RuntimeException("Image with ID: " + imageUpdateModel.getId() + " not found");
    }

    public void delete(Long id) {
        imageRepository.deleteById(id);
    }
}
