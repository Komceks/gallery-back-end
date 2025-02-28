package lt.restservice.bl;

import java.util.Collection;
import java.util.Set;

import lt.restservice.model.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findByNameIn(Collection<String> names);
}
