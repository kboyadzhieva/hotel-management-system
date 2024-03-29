package api.helper.creator;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomRequest;

public class RoomCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public RoomRequest createRoom() {
        String content = "classpath:createRoom.json";
        return objectCreator.createObject(content, RoomRequest.class);
    }

    public RoomRequest createRoomWithInvalidData() {
        String content = "classpath:createRoomWithInvalidData.json";
        return objectCreator.createObject(content, RoomRequest.class);
    }
}
