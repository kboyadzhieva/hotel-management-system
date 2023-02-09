package api.helper.creator;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.car.request.CarRequest;

public class CarCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public CarRequest createCar() {
        String content = "classpath:createCar.json";
        return objectCreator.createObject(content, CarRequest.class);
    }
}
