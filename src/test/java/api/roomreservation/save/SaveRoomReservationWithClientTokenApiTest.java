package api.roomreservation.save;

import api.BaseApiTest;
import api.helper.creator.RoomReservationCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationSaveResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class SaveRoomReservationWithClientTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms/{id}/reservations";
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void saveRoomReservationWithClientTokenShouldReturnCreated() {
        RoomReservationRequestSave roomReservation = roomReservationCreator.createRoomReservation();

        RoomReservationSaveResponse roomReservationResponse =
                getClientWithClientToken()
                        .contentType(ContentType.JSON)
                        .body(roomReservation)
                        .when()
                        .pathParam("id", 1L)
                        .post(URI)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(RoomReservationSaveResponse.class);

        Long id = roomReservationResponse.getRoom().getId();
        Long rid = roomReservationResponse.getId();
        deleteRoomReservationAfterTest(id, rid);
    }

    @Test
    public void saveRoomReservationWithInvalidDataByClientShouldReturnBadRequest() {
        RoomReservationRequestSave roomReservation = roomReservationCreator
                .createRoomReservationWithInvalidData();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .pathParam("id", 1L)
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void deleteRoomReservationAfterTest(Long id, Long rid) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .pathParam("rid", rid)
                .delete(URI + "/{rid}");
    }
}
