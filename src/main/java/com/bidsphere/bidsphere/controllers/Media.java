package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.components.RESTResponse;
import com.bidsphere.bidsphere.services.ImageCleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@RestController
@CrossOrigin
public class Media {

    private static final String UPLOAD_DIR = "/home/ubuntu/cdn/";

    private ImageCleanup imageCleanup;

    @Autowired
    public void setImageCleanup(ImageCleanup imageCleanup) {
        this.imageCleanup = imageCleanup;
    }

    public static class ImageRequest {
        public String image;
    }

    @PostMapping("/media/save")
    public RESTResponse<String> saveMedia(@RequestBody ImageRequest imageRequest) throws IOException {
        try {
            UUID imageUUID = UUID.randomUUID();
            String filePath = UPLOAD_DIR + imageUUID.toString() + ".png";
            byte[] decodedBytes = Base64.getDecoder().decode(imageRequest.image);
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                return RESTResponse.failed("Unsupported image format or corrupted file");
            }

            File imageFile = new File(filePath);
            ImageIO.write(image, "png", imageFile);

            imageCleanup.addImage(imageUUID.toString());

            return RESTResponse.passed(imageUUID.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return RESTResponse.failed("File upload failed!");
        }
    }
}
