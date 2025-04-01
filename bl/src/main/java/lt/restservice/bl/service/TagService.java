package lt.restservice.bl.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lt.restservice.bl.repository.TagRepository;
import lt.restservice.model.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public Set<Tag> findOrCreateTags(Set<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return new HashSet<>();
        }
        Set<Tag> existingTags = new HashSet<>(tagRepository.findByNameIn(tagNames));
        Map<String, Tag> tagMap = existingTags.stream()
                .collect(Collectors.toMap(Tag::getName, Function.identity()));

        Set<Tag> result = tagNames.stream()
                .map(tagName -> tagMap.computeIfAbsent(tagName, tag -> new Tag(tagName)))
                .collect(Collectors.toSet());

        tagRepository.saveAll(result);

        return result;
    }
}
