package api.room.delete;

import api.BaseApiTest;
import api.helper.creator.RoomCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class DeleteRoomWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();

    @Test
    public void deleteRoomByIdWithAdminTokenShouldReturnNoContent() {
        Long savedRoomId = saveRoomBeforeTest();

        getClientWithAdminToken()
                .when()
                .pathParam("id", savedRoomId)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteRoomByNonExistentIdWithAdminTokenShouldReturnNotFound() {
        Long id = 10000000L;

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
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
}
