package api.roomreservation.get;

import api.BaseApiTest;
import api.helper.creator.RoomCreator;
import api.helper.creator.RoomReservationCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationSaveResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class GetRoomReservationWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void getRoomReservationWithAdminTokenShouldReturnOk() {
        Long id = saveRoomBeforeTest();
        Long rid = saveRoomReservationBeforeTest(id);

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .pathParam("rid", rid)
                .get(URI + "/{id}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        deleteRoomAfterTest(id);
    }

    @Test
    public void getRoomReservationByNonExistentIdWithAdminTokenShouldReturnNotFound() {
        Long id = saveRoomBeforeTest();
        Long rid = 10000078L;

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .pathParam("rid", rid)
                .get(URI + "/{id}/reservations/{rid}")
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
