package api.room;

import api.BaseApiTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class GetRoomWithClientTokenApiTest extends BaseApiTest {

    private static final String URI = "/rooms/{id}";

    @Test
    public void getRoomByIdWithClientTokenShouldReturnOk() {
        getClientWithClientToken()
                .when()
                .pathParam("id", 1)
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getRoomByInvalidIdWithClientTokenShouldReturnBadRequest() {
        getClientWithClientToken()
                .when()
                .pathParam("id", "a")
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getRoomByNonExistentIdWithClientTokenShouldReturnNotFound() {
        Long id = 10000000L;

        getClientWithClientToken()
                .when()
                .pathParam("id", id)
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
