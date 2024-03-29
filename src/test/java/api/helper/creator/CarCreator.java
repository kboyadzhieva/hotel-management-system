package api.helper.creator;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarRequest;

public class CarCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public CarRequest createCar() {
        String content = "classpath:createCar.json";
        return objectCreator.createObject(content, CarRequest.class);
    }

    public CarRequest createCarWithInvalidData() {
        String content = "classpath:createCarWithInvalidData.json";
        return objectCreator.createObject(content, CarRequest.class);
    }
}
