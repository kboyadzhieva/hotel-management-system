package api.roomreservation.update;

import api.BaseApiTest;
import api.helper.creator.RoomReservationCreator;
import api.helper.updater.RoomReservationUpdater;
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

    private static final String URI = "/rooms/{id}/reservations";
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();
    private final RoomReservationUpdater roomReservationUpdater = new RoomReservationUpdater();

    @Test
    public void updateRoomReservationWithAdminTokenShouldReturnOk() {
        Long savedRoomReservationId = saveRoomReservationBeforeTest();
        RoomReservationRequestUpdate updatedRoom = roomReservationUpdater.updateRoomReservation();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(updatedRoom)
                .when()
                .pathParam("id", 1L)
                .pathParam("rid", savedRoomReservationId)
                .put(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        deleteRoomReservationAfterTest(savedRoomReservationId);
    }

    @Test
    public void updateRoomReservationWithInvalidDataByAdminShouldReturnBadRequest() {
        Long savedRoomReservationId = saveRoomReservationBeforeTest();
        RoomReservationRequestUpdate updatedRoom = roomReservationUpdater
                .updateRoomReservationWithInvalidData();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(updatedRoom)
                .when()
                .pathParam("id", 1L)
                .pathParam("rid", savedRoomReservationId)
                .put(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        deleteRoomReservationAfterTest(savedRoomReservationId);
    }

    @Test
    public void updateNonExistentRoomReservationWithAdminTokenShouldReturnNotFound() {
        Long rid = 10000020L;
        RoomReservationRequestUpdate updatedRoom = roomReservationUpdater
                .updateRoomReservationWithInvalidData();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(updatedRoom)
                .when()
                .pathParam("id", 1L)
                .pathParam("rid", rid)
                .put(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private Long saveRoomReservationBeforeTest() {
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        RoomReservationSaveResponse roomReservationResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(roomReservation)
                        .when()
                        .pathParam("id", 1L)
                        .post(URI)
                        .then()
                        .extract()
                        .as(RoomReservationSaveResponse.class);

        return roomReservationResponse.getId();
    }

    private void deleteRoomReservationAfterTest(Long rid) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", 1L)
                .pathParam("rid", rid)
                .delete(URI + "/{rid}");
    }
}
