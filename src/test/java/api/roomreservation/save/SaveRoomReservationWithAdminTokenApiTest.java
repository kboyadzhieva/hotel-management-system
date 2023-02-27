package api.roomreservation.save;

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
public class SaveRoomReservationWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void saveRoomReservationWithAdminTokenShouldReturnCreated() {
        Long id = saveRoomBeforeTest();
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        RoomReservationSaveResponse roomReservationResponse =
        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", id)
                .post(URI + "/{id}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RoomReservationSaveResponse.class);

        deleteRoomAfterTest(id);
    }

    @Test
    public void saveRoomReservationWithInvalidDataByAdminShouldReturnBadRequest() {
        Long id = saveRoomBeforeTest();
        RoomReservationRequestSave roomReservation = roomReservationCreator
                .createRoomReservationWithInvalidData();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", id)
                .post(URI + "/{id}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        deleteRoomAfterTest(id);
    }

    @Test
    public void saveRoomReservationForNonExistentRoomWithAdminTokenShouldReturnNotFound() {
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", 100000000002L)
                .post(URI + "/{id}/reservations")
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

    private void deleteRoomAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
