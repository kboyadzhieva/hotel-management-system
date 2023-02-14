package com.moonlighthotel.hotelmanagementsystem.converter.roomreservation;

import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.RoomImage;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomImageConverter {

    public Set<RoomImage> toSetOfImages(Set<String> images) {
        return images.stream()
                .map(image -> RoomImage.builder().path(image).build())
                .collect(Collectors.toSet());
    }

    public Set<String> toSetOfStrings(Set<RoomImage> images) {
        return images.stream()
                .map(RoomImage::getPath)
                .collect(Collectors.toSet());
    }
}
