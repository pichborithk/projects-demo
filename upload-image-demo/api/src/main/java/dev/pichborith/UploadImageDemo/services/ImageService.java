package dev.pichborith.UploadImageDemo.services;

import dev.pichborith.UploadImageDemo.models.Image;
import dev.pichborith.UploadImageDemo.repositories.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;


    public Image storeImage(MultipartFile file) throws IOException {
        // Convert MultipartFile to byte array
        byte[] imageData = file.getBytes();

        Image image = new Image(imageData);
        return imageRepo.save(image);
    }

    public Image getImage(int id) {
        return imageRepo.findById(id).orElse(null);
    }
}
