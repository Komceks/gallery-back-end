package lt.restservice.bl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import lt.restservice.model.Author;
import lt.restservice.model.Image;
import lt.restservice.model.Tag;

@Slf4j
public class ImageUploadMapper {

    public static Image toImage(ImageUploadDto dto, MultipartFile multipartFile,
            AuthorService authorService, TagService tagService) throws IOException {

        Set<String> tags = dto.getTags();

        Set<Tag> tagSet = new HashSet<>();

        if (CollectionUtils.isNotEmpty(tags)) {
            tagSet = tags.stream().map(tagService::findOrCreateTag).collect(Collectors.toSet());
        }

        Author author = authorService.findOrCreateAuthor(dto.getAuthorName());

        return new Image(multipartFile.getBytes(), dto.getImageName(), dto.getDescription(),
                dto.getDate(), author, tagSet, dto.getUploadDate());
    }
}
