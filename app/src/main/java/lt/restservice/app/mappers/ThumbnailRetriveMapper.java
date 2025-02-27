package lt.restservice.app.mappers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.ThumbnailRetrieveDto;
import lt.restservice.bl.AuthorService;
import lt.restservice.bl.ImageService;
import lt.restservice.bl.TagService;
import lt.restservice.model.Tag;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThumbnailRetriveMapper {

    private final ImageService imageService;

    @Transactional
    public List<ThumbnailRetrieveDto> toThumbnailRetrieveDto(int startIdx, int endIdx) {

        return imageService.getImageRequestBatch(startIdx, endIdx).stream().map(imageCreateReq ->
                new ThumbnailRetrieveDto(
                        imageCreateReq.getImageName(),
                        imageCreateReq.getDescription(),
                        imageCreateReq.getAuthor().getName(),
                        imageCreateReq.getDate(),
                        imageCreateReq.getTags().stream().map(Tag::getName).collect(Collectors.toSet()),
                        imageCreateReq.getUploadDate(),
                        imageCreateReq.getThumbnail())
        ).toList();
    }
}
