package lt.restservice.bl.services;

import org.apache.commons.lang3.StringUtils;

import lombok.RequiredArgsConstructor;

import lt.restservice.bl.repositories.AuthorRepository;
import lt.restservice.model.Author;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author findOrCreateAuthor(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Author name must not be null or empty.");
        }
        return authorRepository.findByName(name).orElseGet(() -> new Author(name));
    }

}
