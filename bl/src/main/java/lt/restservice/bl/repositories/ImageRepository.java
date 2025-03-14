package lt.restservice.bl.repositories;

import lt.restservice.model.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, CustomImageRepository {

}
