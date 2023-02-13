package com.moonlighthotel.hotelmanagementsystem.converter.transfer;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarImage;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CarImageConverter {

    public Set<CarImage> toSetOfImages(Set<String> images) {
        return images.stream()
                .map(image -> CarImage.builder().path(image).build())
                .collect(Collectors.toSet());
    }

    public Set<String> toSetOfStrings(Set<CarImage> images) {
        return images.stream()
                .map(CarImage::getPath)
                .collect(Collectors.toSet());
    }
}
