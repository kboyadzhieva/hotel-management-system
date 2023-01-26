package api.room;

import api.BaseApiTest;
import api.room.creator.RoomCreator;
import api.room.updater.RoomUpdater;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class UpdateRoomWithoutTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomUpdater roomUpdater = new RoomUpdater();

    @Test
    public void updateRoomWithoutTokenShouldReturnUnauthorized() {
        Long savedRoomId = saveRoomBeforeTest();
        RoomRequest updatedRoom = roomUpdater.updateRoom();

        given()
                .contentType(ContentType.JSON)
                .body(updatedRoom)
                .when()
                .pathParam("id", savedRoomId)
                .put(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

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
