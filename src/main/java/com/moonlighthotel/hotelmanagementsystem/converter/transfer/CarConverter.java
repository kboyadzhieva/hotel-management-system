package com.moonlighthotel.hotelmanagementsystem.converter.transfer;

import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.response.CarResponse;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import com.moonlighthotel.hotelmanagementsystem.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CarConverter {

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final CarImageConverter imageConverter;

    @Autowired
    private final DateFormatter dateFormatter;

    @Autowired
    private final CarCategoryConverter carCategoryConverter;

    public Car toCar(CarRequest carRequest) {
        CarCategory foundCarCategory = categoryService.findById(carRequest.getCategory());

        return Car.builder()
                .carCategory(foundCarCategory)
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .image(carRequest.getImage())
                .images(imageConverter.toSetOfImages(carRequest.getImages()))
                .year(carRequest.getYear())
                .build();
    }

    public CarResponse toCarResponse(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .images(imageConverter.toSetOfStrings(car.getImages()))
                .year(car.getYear())
                .created(dateFormatter.instantToString(car.getCreated()))
                .category(carCategoryConverter.toCategoryResponse(car.getCarCategory()))
                .build();
    }
}
