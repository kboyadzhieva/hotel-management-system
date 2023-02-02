package api.roomreservation.summarize;

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

@RunWith(JUnit4.class)
public class SummarizeRoomReservationWithClientTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void summarizeRoomReservationWithClientTokenShouldReturnOk() {
        Long id = saveRoomBeforeTest();
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", id)
                .post(URI + "/{id}/summarize")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        deleteRoomAfterTest(id);
    }

    @Test
    public void summarizeRoomReservationForNonExistentRoomIdWithClientTokenShouldReturnNotFound() {
        Long id = 123456789032165L;
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", id)
                .post(URI + "/{id}/summarize")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void summarizeRoomReservationForInvalidRoomIdWithClientTokenShouldReturnBadRequest() {
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", "b")
                .post(URI + "/{id}/summarize")
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

    private void deleteRoomAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
