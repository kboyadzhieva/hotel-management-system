package api.user;

import api.BaseApiTest;
import api.user.creator.UserCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestCreate;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class DeleteUserWithoutTokenApiTest extends BaseApiTest {

    private static final String URI = "/users";
    private final UserCreator userCreator = new UserCreator();

    @Test
    public void validateThatDeleteUserWithoutAuthenticationReturnsUnauthorized() {
        Long savedUserId = saveUserBeforeTest();

        given()
                .when()
                .pathParam("id", savedUserId)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

        deleteUserAfterTest(savedUserId);
    }

    private Long saveUserBeforeTest() {
        UserRequestCreate user = userCreator.createUser();

        UserResponse userResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                        .when()
                        .post(URI)
                        .then()
                        .extract()
                        .as(UserResponse.class);

        return userResponse.getId();
    }

    private void deleteUserAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
