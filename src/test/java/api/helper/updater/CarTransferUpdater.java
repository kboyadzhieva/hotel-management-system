package api.helper.updater;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarTransferRequest;

public class CarTransferUpdater {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public CarTransferRequest updateCarTransfer() {
        String content = "classpath:createCarTransfer.json";
        return objectCreator.createObject(content, CarTransferRequest.class);
    }

    public CarTransferRequest updateCarTransferWithInvalidData() {
        String content = "classpath:createCarTransferWithInvalidData.json";
        return objectCreator.createObject(content, CarTransferRequest.class);
    }
}
