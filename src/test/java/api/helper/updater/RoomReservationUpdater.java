package api.helper.updater;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestUpdate;

public class RoomReservationUpdater {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public RoomReservationRequestUpdate updateRoomReservation() {
        String content = "classpath:updateRoomReservation.json";
        return objectCreator.createObject(content, RoomReservationRequestUpdate.class);
    }

    public RoomReservationRequestUpdate updateRoomReservationWithInvalidData() {
        String content = "classpath:updateRoomReservationWithInvalidData.json";
        return objectCreator.createObject(content, RoomReservationRequestUpdate.class);
    }
}
