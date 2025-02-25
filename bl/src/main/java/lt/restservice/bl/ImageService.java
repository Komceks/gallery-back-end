package lt.restservice.bl;

import lt.restservice.model.Image;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final TagService tagService;
    private final AuthorService authorService;

    @Transactional
    public void uploadImage(ImageUploadDto dto, MultipartFile multipartFile) throws IOException {
        try {

            log.debug("Uploading image {}", dto);

            Image image = ImageUploadMapper.toImage(dto, multipartFile, authorService, tagService);

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
}
