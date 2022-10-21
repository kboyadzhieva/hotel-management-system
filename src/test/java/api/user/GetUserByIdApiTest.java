package api.user;

import api.BaseApiTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class GetUserByIdApiTest extends BaseApiTest {

    private static final String URI = "/users/{id}";

    @Test
    public void getUserByIdWithoutTokenShouldReturnUnauthorized() {
        given()
                .when()
                .pathParam("id", 1)
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getUserByIdWithAdminTokenShouldReturnOK() {
        getClientWithAdminToken()
                .when()
                .pathParam("id", 1)
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getUserByIdWithClientTokenShouldReturnForbidden() {
        getClientWithClientToken()
                .when()
                .pathParam("id", 1)
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
