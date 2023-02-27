package api.roomreservation.update;

import api.BaseApiTest;
import api.helper.creator.RoomCreator;
import api.helper.creator.RoomReservationCreator;
import api.helper.updater.RoomReservationUpdater;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestUpdate;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationSaveResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class UpdateRoomReservationWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();
    private final RoomReservationUpdater roomReservationUpdater = new RoomReservationUpdater();

    @Test
    public void updateRoomReservationWithAdminTokenShouldReturnOk() {
        Long id = saveRoomBeforeTest();
        Long rid = saveRoomReservationBeforeTest(id);
        RoomReservationRequestUpdate updatedRoom = roomReservationUpdater.updateRoomReservation();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(updatedRoom)
                .when()
                .pathParam("id", id)
                .pathParam("rid", rid)
                .put(URI + "/{id}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        deleteRoomAfterTest(id);
    }

    @Test
    public void updateRoomReservationWithInvalidDataByAdminShouldReturnBadRequest() {
        Long id = saveRoomBeforeTest();
        Long rid = saveRoomReservationBeforeTest(id);
        RoomReservationRequestUpdate updatedRoom = roomReservationUpdater
                .updateRoomReservationWithInvalidData();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(updatedRoom)
                .when()
                .pathParam("id", id)
                .pathParam("rid", rid)
                .put(URI + "/{id}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        deleteRoomAfterTest(id);
    }

    @Test
    public void updateNonExistentRoomReservationWithAdminTokenShouldReturnNotFound() {
        Long id = saveRoomBeforeTest();
        Long rid = 10000020L;
        RoomReservationRequestUpdate updatedRoom = roomReservationUpdater
                .updateRoomReservationWithInvalidData();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(updatedRoom)
                .when()
                .pathParam("id", id)
                .pathParam("rid", rid)
                .put(URI + "/{id}/reservations/{rid}")
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
