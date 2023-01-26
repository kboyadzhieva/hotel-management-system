package api.room.save;

import api.BaseApiTest;
import api.room.helper.creator.RoomCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class SaveRoomByAdminApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();

    @Test
    public void saveRoomByAdminShouldReturnCreated() {
        RoomRequest room = roomCreator.createRoom();

        RoomResponse roomResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(room)
                        .when()
                        .post(URI)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(RoomResponse.class);

        Long id = roomResponse.getId();
        deleteRoomAfterTest(id);
    }

    @Test
    public void saveRoomWithInvalidDataByAdminShouldReturnBadRequest() {
        RoomRequest room = roomCreator.createRoomWithInvalidData();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(room)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void deleteRoomAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
