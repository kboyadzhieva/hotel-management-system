package com.moonlighthotel.hotelmanagementsystem.converter;

import com.moonlighthotel.hotelmanagementsystem.model.Image;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ImageConverter {

    public Set<Image> toSetOfImages(Set<String> images) {
        return images.stream()
                .map(image -> Image.builder().path(image).build())
                .collect(Collectors.toSet());
    }

    public Set<String> toSetOfStrings(Set<Image> images) {
        return images.stream()
                .map(Image::getPath)
                .collect(Collectors.toSet());
    }
}
