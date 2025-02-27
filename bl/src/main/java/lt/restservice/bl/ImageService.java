package lt.restservice.bl;

import java.util.List;

import lt.restservice.model.Image;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public void uploadImage(ImageCreateReq imageReq) {
        try {

            Image image = new Image(imageReq.getImageFile(), imageReq.getImageName(), imageReq.getDescription(),
                    imageReq.getDate(), imageReq.getAuthor(), imageReq.getTags(),
                    imageReq.getUploadDate(), imageReq.getThumbnail());

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
    public List<ImageCreateReq> getImageRequestBatch(int startIdx, int endIdx) {
        return imageRepository.findAll(PageRequest.of(startIdx, endIdx)).stream().map(
                image -> new ImageCreateReq(
                        image.getImageBlob(),
                        image.getName(),
                        image.getDescription(),
                        image.getDate(),
                        image.getAuthor(),
                        image.getTags(),
                        image.getUploadDate(),
                        image.getThumbnail())
        ).toList();
    }
}
