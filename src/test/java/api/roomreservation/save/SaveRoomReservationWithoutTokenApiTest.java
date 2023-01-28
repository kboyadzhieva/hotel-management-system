package api.roomreservation.save;

import api.helper.creator.RoomReservationCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class SaveRoomReservationWithoutTokenApiTest {

    private static final String URI = "/{id}/reservations";
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void saveRoomReservationWithoutTokenShouldReturnUnauthorized() {
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        given()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", 1L)
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
