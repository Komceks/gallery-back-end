package lt.restservice.bl;

import lt.restservice.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Tag findOrCreateTag(String name) {

        if (name == null || name.trim().isEmpty()) {

            throw new IllegalArgumentException("Tag name must not be null or empty.");
        }

        return tagRepository.findByName(name).orElseGet(() -> {

            Tag newTag = new Tag(name);

            try {
                return tagRepository.save(newTag);

            } catch (Exception e) {

                throw new IllegalStateException("Failed to save new Tag: " + name, e);
            }
        });
    }
}
