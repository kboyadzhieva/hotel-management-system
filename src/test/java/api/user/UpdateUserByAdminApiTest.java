package api.user;

import api.BaseApiTest;
import api.user.creator.UserCreator;
import api.user.updater.UserUpdater;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestCreate;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestUpdate;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class UpdateUserByAdminApiTest extends BaseApiTest {

    private static final String URI = "/users";
    private final UserCreator userCreator = new UserCreator();
    private final UserUpdater userUpdater = new UserUpdater();

    @Test
    public void validateThatUpdateUserByAdminShouldReturnOK() {
        Long savedUserId = saveUserBeforeTest();
        UserRequestUpdate user = userUpdater.updateUserByAdmin();

        UserResponse userResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(user)
                        .when()
                        .pathParam("id", savedUserId)
                        .put(URI + "/{id}")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(UserResponse.class);

        Long id = userResponse.getId();
        deleteUserAfterTest(id);
    }

    @Test
    public void validateThatUpdateUserByAdminWithInvalidDataShouldReturnBadRequest() {
        Long savedUserId = saveUserBeforeTest();
        UserRequestUpdate user = userUpdater.updateUserByAdminWithInvalidData();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .pathParam("id", savedUserId)
                .put(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        deleteUserAfterTest(savedUserId);
    }

    private Long saveUserBeforeTest() {
        UserRequestCreate user = userCreator.createUserByAdmin();

        UserResponse userResponse =
                getClientWithAdminToken()
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
