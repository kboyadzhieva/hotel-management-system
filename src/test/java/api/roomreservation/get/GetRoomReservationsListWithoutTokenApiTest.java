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

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class GetRoomReservationsListWithoutTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void getRoomReservationsListWithoutTokenShouldReturnUnauthorized() {
        Long id = saveRoomBeforeTest();
        saveRoomReservationBeforeTest(id);

        given()
                .when()
                .pathParam("id", id)
                .get(URI + "/{id}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

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
