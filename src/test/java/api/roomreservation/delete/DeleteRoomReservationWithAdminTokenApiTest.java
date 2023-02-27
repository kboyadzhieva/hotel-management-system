package api.roomreservation.delete;

import api.BaseApiTest;
import api.helper.creator.RoomCreator;
import api.helper.creator.RoomReservationCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationSaveResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class DeleteRoomReservationWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void deleteRoomReservationWithAdminTokenShouldReturnNoContent() {
        Long id = saveRoomBeforeTest();
        Long rid = saveRoomReservationBeforeTest(id);

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .pathParam("rid", rid)
                .delete(URI + "/{id}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());

        deleteRoomAfterTest(id);
    }

    @Test
    public void deleteNonExistentRoomReservationWithAdminTokenShouldReturnNotFound() {
        Long id = saveRoomBeforeTest();
        Long rid = 200011023L;

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .pathParam("rid", rid)
                .delete(URI + "/{id}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

        deleteRoomAfterTest(id);
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

    private Long saveRoomReservationBeforeTest(Long id) {
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        RoomReservationSaveResponse roomReservationResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(roomReservation)
                        .when()
                        .pathParam("id", id)
                        .post(URI + "/{id}/reservations")
                        .then()
                        .extract()
                        .as(RoomReservationSaveResponse.class);

        return roomReservationResponse.getId();
    }

    private void deleteRoomAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
