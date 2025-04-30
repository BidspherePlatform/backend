package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.services.ImageCleanup;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/media")
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

    @PostMapping("/upload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid image format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> saveMedia(@RequestBody ImageRequest imageRequest) throws IOException {
        try {
            UUID imageUUID = UUID.randomUUID();
            String filePath = UPLOAD_DIR + imageUUID.toString() + ".png";
            byte[] decodedBytes = Base64.getDecoder().decode(imageRequest.image);
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                return ResponseEntity.badRequest().build();
            }

            File imageFile = new File(filePath);
            ImageIO.write(image, "png", imageFile);

            imageCleanup.addImage(imageUUID.toString());

            return ResponseEntity.ok(imageUUID.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
