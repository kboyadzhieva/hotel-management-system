package api.helper.creator;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarTransferRequest;

public class CarTransferCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public CarTransferRequest createCarTransfer() {
        String content = "classpath:createCarTransfer.json";
        return objectCreator.createObject(content, CarTransferRequest.class);
    }
}
