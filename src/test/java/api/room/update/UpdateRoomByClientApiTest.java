package api.room.update;

import api.BaseApiTest;
import api.helper.creator.RoomCreator;
import api.helper.updater.RoomUpdater;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class UpdateRoomByClientApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomUpdater roomUpdater = new RoomUpdater();

    @Test
    public void updateRoomByClientShouldReturnForbidden() {
        Long savedRoomId = saveRoomBeforeTest();
        RoomRequest updatedRoom = roomUpdater.updateRoom();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(updatedRoom)
                .when()
                .pathParam("id", savedRoomId)
                .put(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteRoomAfterTest(savedRoomId);
    }

    private Long saveRoomBeforeTest() {
        RoomRequest room = roomCreator.createRoom();

        RoomResponse roomResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(room)
                        .when()
                        .post(URI)
                        .then()
                        .extract()
                        .as(RoomResponse.class);

        return roomResponse.getId();
    }

    private void deleteRoomAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
