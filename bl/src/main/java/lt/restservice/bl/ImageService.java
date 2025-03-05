package lt.restservice.bl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lt.restservice.model.Author;
import lt.restservice.model.Image;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import lt.restservice.model.Tag;

import org.hibernate.exception.ConstraintViolationException;

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
    public void uploadImage(CreateImageModel imageModel) {
        try {

            Set<Tag> tagSet = tagService.findOrCreateTags(imageModel.getTagNames());

            Author author = authorService.findOrCreateAuthor(imageModel.getAuthorName());

            Image image = new Image(imageModel.getImageFile(), imageModel.getImageName(), imageModel.getDescription(),
                    imageModel.getDate(), author, tagSet, imageModel.getUploadDate(), imageModel.getThumbnail());

            log.debug("Saving image: {}", image);

            imageRepository.save(image);

        } catch (ConstraintViolationException ex) {

            log.error("Constraint violation during image save:");
            throw ex;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public List<CreateImageModel> getImageRequestBatch(int startIdx, int endIdx) {
        return imageRepository.findAll(PageRequest.of(startIdx, endIdx)).stream().map(
                image -> CreateImageModel.builder()
                        .id(image.getId())
                        .imageFile(image.getImageBlob())
                        .imageName(image.getName())
                        .description(image.getDescription())
                        .date(image.getDate())
                        .authorName(image.getAuthor().getName())
                        .tagNames(image.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                        .uploadDate(image.getUploadDate())
                        .thumbnail(image.getThumbnail())
                        .build()
        ).toList();
    }

    @Transactional
    public List<CreateImageModel> getImageRequestBatch(int startIdx, int endIdx, String query) {
        return imageRepository.searchImages(startIdx, endIdx, query).stream().map(
                image -> CreateImageModel.builder()
                        .id(image.getId())
                        .imageFile(image.getImageBlob())
                        .imageName(image.getName())
                        .description(image.getDescription())
                        .date(image.getDate())
                        .authorName(image.getAuthor().getName())
                        .tagNames(image.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                        .uploadDate(image.getUploadDate())
                        .thumbnail(image.getThumbnail())
                        .build()
        ).toList();
    }
}
