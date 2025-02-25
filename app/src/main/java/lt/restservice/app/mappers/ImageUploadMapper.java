package lt.restservice.app.mappers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.restservice.app.dto.ImageUploadDto;
import lt.restservice.bl.AuthorService;
import lt.restservice.bl.ImageCreateReq;
import lt.restservice.bl.ImageService;
import lt.restservice.bl.TagService;
import lt.restservice.bl.ThumbnailGenerator;
import lt.restservice.model.Author;
import lt.restservice.model.Tag;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUploadMapper {

    private final ImageService imageService;
    private final AuthorService authorService;
    private final TagService tagService;

    @Transactional
    public void toImageReq(ImageUploadDto dto, MultipartFile multipartFile) throws IOException {

        Set<String> tags = dto.getTags();

        Set<Tag> tagSet = new HashSet<>();

        if (CollectionUtils.isNotEmpty(tags)) {
            tagSet = tags.stream().map(tagService::findOrCreateTag).collect(Collectors.toSet());
        }

        Author author = authorService.findOrCreateAuthor(dto.getAuthorName());

        byte[] thumbnail = ThumbnailGenerator.createThumbnail(multipartFile.getBytes());

        ImageCreateReq imageReq = new ImageCreateReq(multipartFile.getBytes(), dto.getImageName(), dto.getDescription(),
                dto.getDate(), author, tagSet, dto.getUploadDate(), thumbnail);

        imageService.uploadImage(imageReq);
    }
}
