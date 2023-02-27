package api.room.delete;

import api.BaseApiTest;
import api.helper.creator.RoomCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class DeleteRoomWithClientTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();

    @Test
    public void deleteRoomByIdWithClientTokenShouldReturnForbidden() {
        Long savedRoomId = saveRoomBeforeTest();

        getClientWithClientToken()
                .when()
                .pathParam("id", savedRoomId)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteRoomAfterTest(savedRoomId);
    }

    @Test
    public void deleteRoomByNonExistentIdWithClientTokenShouldReturnForbidden() {
        Long id = 200030000L;

        getClientWithClientToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
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
