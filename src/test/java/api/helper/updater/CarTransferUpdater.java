package api.helper.updater;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarTransferRequest;

public class CarTransferUpdater {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public CarTransferRequest updateCarTransfer() {
        String content = "classpath:updateCarTransfer.json";
        return objectCreator.createObject(content, CarTransferRequest.class);
    }

    public CarTransferRequest updateCarTransferWithInvalidData() {
        String content = "classpath:updateCarTransferWithInvalidData.json";
        return objectCreator.createObject(content, CarTransferRequest.class);
    }
}
