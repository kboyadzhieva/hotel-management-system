package api.room;

import api.room.creator.RoomCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class SaveRoomWithoutTokenApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();

    @Test
    public void saveRoomWithoutTokenShouldReturnUnauthorized() {
        RoomRequest room = roomCreator.createRoom();

        given()
                .contentType(ContentType.JSON)
                .body(room)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
