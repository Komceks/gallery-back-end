package lt.restservice.app.mappers;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.ThumbnailRetrieveDto;
import lt.restservice.bl.ImageService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThumbnailRetriveMapper {

    private final ImageService imageService;

    @Transactional
    public List<ThumbnailRetrieveDto> toThumbnailRetrieveDto(int startIdx, int endIdx) {

        return imageService.getImageRequestBatch(startIdx, endIdx).stream().map(createImageModel ->
                new ThumbnailRetrieveDto(
                        createImageModel.getImageName(),
                        createImageModel.getDescription(),
                        createImageModel.getAuthorName(),
                        createImageModel.getDate(),
                        createImageModel.getTagNames(),
                        createImageModel.getUploadDate(),
                        createImageModel.getThumbnail())
        ).toList();
    }
}
