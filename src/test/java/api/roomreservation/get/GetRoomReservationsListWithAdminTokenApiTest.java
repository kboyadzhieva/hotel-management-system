package api.roomreservation.get;

import api.BaseApiTest;
import api.helper.creator.RoomCreator;
import api.helper.creator.RoomReservationCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class GetRoomReservationsListWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void getRoomReservationsListWithAdminTokenShouldReturnOk() {
        Long id = saveRoomBeforeTest();
        saveRoomReservationBeforeTest(id);

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .get(URI + "/{id}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        deleteRoomAfterTest(id);
    }

    @Test
    public void getRoomReservationsListWithAdminTokenShouldReturnList() {
        Long id = saveRoomBeforeTest();
        saveRoomReservationBeforeTest(id);

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .get(URI + "/{id}/reservations")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .body("size()", is(1));

        deleteRoomAfterTest(id);
    }

    @Test
    public void getRoomReservationsForNonExistentRoomShouldReturnNotFound() {
        Long id = 123456789000L;

        getClientWithAdminToken()
                .pathParam("id", id)
                .get(URI + "/{id}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getRoomReservationsByInvalidRoomIdWithAdminTokenShouldReturnBadRequest() {
        getClientWithAdminToken()
                .pathParam("id", "a")
                .get(URI + "/{id}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
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

    private void saveRoomReservationBeforeTest(Long id) {
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", id)
                .post(URI + "/{id}/reservations");
    }

    private void deleteRoomAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
