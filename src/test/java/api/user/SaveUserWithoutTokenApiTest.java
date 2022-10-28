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
public class SaveUserWithoutTokenApiTest extends BaseApiTest {

    private static final String URI = "/users";
    private final UserCreator userCreator = new UserCreator();

    @Test
    public void saveUserWithoutTokenShouldReturnCreated() {
        UserRequestCreate user = userCreator.createUser();

        UserResponse userResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                        .when()
                        .post(URI)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(UserResponse.class);

        Long id = userResponse.getId();
        deleteUserAfterTest(id);
    }

    @Test
    public void saveUserWithInvalidDataShouldReturnBadRequest() {
        UserRequestCreate user = userCreator.createUserWithInvalidData();

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void deleteUserAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
