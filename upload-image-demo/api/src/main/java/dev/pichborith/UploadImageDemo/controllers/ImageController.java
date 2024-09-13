package dev.pichborith.UploadImageDemo.controllers;

import dev.pichborith.UploadImageDemo.models.Image;
import dev.pichborith.UploadImageDemo.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "${CLIENT_URL}")
public class ImageController {

    @Autowired
    private ImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Image image = imageService.storeImage(file);
            return ResponseEntity.ok("Image uploaded successfully. ID: " + image.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Image image = imageService.getImage(id);

        if (image != null) {
            return ResponseEntity.ok()
                                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image_" + image.getId() + "\"")
                                 .contentType(
                                     MediaType.IMAGE_JPEG)  // or use appropriate content type
                                 .body(image.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
