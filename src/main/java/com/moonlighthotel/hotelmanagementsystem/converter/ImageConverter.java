package com.moonlighthotel.hotelmanagementsystem.converter;

import com.moonlighthotel.hotelmanagementsystem.model.room.RoomImage;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ImageConverter {

    public Set<RoomImage> toSetOfImages(Set<String> images) {
        return images.stream()
                .map(image -> RoomImage.builder().path(image).build())
                .collect(Collectors.toSet());
    }

    public Set<String> toSetOfStrings(Set<RoomImage> roomImages) {
        return roomImages.stream()
                .map(RoomImage::getPath)
                .collect(Collectors.toSet());
    }
}
