package lt.restservice.bl.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import lt.restservice.bl.repository.custom.CustomTagRepository;
import lt.restservice.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, CustomTagRepository, JpaSpecificationExecutor<Tag> {
    Set<Tag> findByNameIn(Collection<String> names);
}
