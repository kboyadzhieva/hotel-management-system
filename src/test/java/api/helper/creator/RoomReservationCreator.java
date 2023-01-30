package api.helper.creator;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;

public class RoomReservationCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public RoomReservationRequestSave createRoomReservation() {
        String content = "classpath:createRoomReservation.json";
        return objectCreator.createObject(content, RoomReservationRequestSave.class);
    }

    public RoomReservationRequestSave createRoomReservationWithInvalidData() {
        String content = "classpath:createRoomReservationWithInvalidData.json";
        return objectCreator.createObject(content, RoomReservationRequestSave.class);
    }
}
