package com.bidsphere.bidsphere.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class ImageCleanup {
    private static final String UPLOAD_DIR = "/home/ubuntu/cdn/";
    private final Map<String, Date> unclaimedImageIds = new HashMap<>();

    public void addImage(String imageUUID) {
        unclaimedImageIds.put(imageUUID, new Date());
    }

    public void claimImage(String imageId) {
        unclaimedImageIds.remove(imageId);
    }

    public void unclaimImages(ArrayList<String> imageIds) {
        deleteImages(imageIds);
        for (String imageId : imageIds) {
            unclaimedImageIds.remove(imageId);
        }
    }

    @Async
    public void performCleanup() {
        if (unclaimedImageIds.isEmpty()) {
            return;
        }

        ArrayList<String> imagesToCleanup = new ArrayList<>();
        for (String imageId : new HashSet<>(unclaimedImageIds.keySet())) {
            Date imageInsertDate = unclaimedImageIds.get(imageId);
            Instant fifteenMinutesAgo = Instant.now().minus(15, ChronoUnit.MINUTES);
            boolean isOld = imageInsertDate.toInstant().isBefore(fifteenMinutesAgo);

            if (isOld) {
                imagesToCleanup.add(imageId);
                unclaimedImageIds.remove(imageId);
            }
        }

        deleteImages(imagesToCleanup);
    }

    @Async
    protected void deleteImages(List<String> imageIds) {
        for (String imageId : imageIds) {
            String imagePath = UPLOAD_DIR + imageId;
            try {
                Path path = Paths.get(imagePath);
                boolean deleted = Files.deleteIfExists(path);
                System.out.println("Deleted " + imagePath + ": " + deleted);
            } catch (IOException e) {
                System.err.println("Error deleting " + imagePath);
            }
        }
    }
}
