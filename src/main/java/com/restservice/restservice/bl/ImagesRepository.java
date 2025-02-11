package com.restservice.restservice.bl;

import com.restservice.restservice.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {

}
