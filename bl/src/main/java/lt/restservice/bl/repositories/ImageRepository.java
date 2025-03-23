package lt.restservice.bl.repositories;

import lt.restservice.bl.repositories.custom.CustomImageRepository;
import lt.restservice.model.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, CustomImageRepository, JpaSpecificationExecutor<Image> {

}
