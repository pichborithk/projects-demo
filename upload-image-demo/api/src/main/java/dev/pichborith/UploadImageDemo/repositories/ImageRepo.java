package dev.pichborith.UploadImageDemo.repositories;

import dev.pichborith.UploadImageDemo.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Integer> {
}
