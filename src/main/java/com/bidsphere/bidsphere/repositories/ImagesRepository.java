package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Images;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImagesRepository extends CrudRepository<Images, UUID> {
}
