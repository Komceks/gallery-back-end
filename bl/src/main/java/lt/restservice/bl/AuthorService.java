package lt.restservice.bl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import lt.restservice.model.Author;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author findOrCreateAuthor(String name) {

        if (StringUtils.isBlank(name)) {

            throw new IllegalArgumentException("Author name must not be null or empty.");
        }

        return authorRepository.findByName(name).orElseGet(() -> new Author(name));
    }

}
