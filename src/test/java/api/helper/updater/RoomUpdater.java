package api.helper.updater;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;

public class RoomUpdater {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public RoomRequest updateRoom() {
        String content = "classpath:createRoom.json";
        return objectCreator.createObject(content, RoomRequest.class);
    }

    public RoomRequest updateRoomWithInvalidData() {
        String content = "classpath:createRoomWithInvalidData.json";
        return objectCreator.createObject(content, RoomRequest.class);
    }
}
