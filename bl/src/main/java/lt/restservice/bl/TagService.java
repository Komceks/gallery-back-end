package lt.restservice.bl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lt.restservice.model.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Set<Tag> findOrCreateTags(Set<String> tagNames) {

        Set<Tag> existingTags = new HashSet<>(tagRepository.findByNameIn(tagNames));

        Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());

        Set<String> missingTagNames = new HashSet<>(tagNames);
        missingTagNames.removeAll(existingTagNames);

        List<Tag> newTags = missingTagNames.stream().map(Tag::new).toList();

        Set<Tag> allTags = new HashSet<>(existingTags);
        allTags.addAll(newTags);

        return allTags;
    }
}
