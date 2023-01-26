package api.room;

import api.BaseApiTest;
import api.room.creator.RoomCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class SaveRoomByClientApiTest extends BaseApiTest {

    private static final String URI = "/rooms";
    private final RoomCreator roomCreator = new RoomCreator();

    @Test
    public void saveRoomByClientShouldReturnForbidden() {
        RoomRequest room = roomCreator.createRoom();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(room)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
