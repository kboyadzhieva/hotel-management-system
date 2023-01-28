package api.user.delete;

import api.BaseApiTest;
import api.helper.creator.UserCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestCreate;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class DeleteUserByClientApiTest extends BaseApiTest {

    private static final String URI = "/users";
    private final UserCreator userCreator = new UserCreator();

    @Test
    public void validateThatDeleteUserByClientReturnsForbidden() {
        Long savedUserId = saveUserBeforeTest();

        getClientWithClientToken()
                .when()
                .pathParam("id", savedUserId)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteUserAfterTest(savedUserId);
    }

    private Long saveUserBeforeTest() {
        UserRequestCreate user = userCreator.createUser();

        UserResponse userResponse =
                getClientWithClientToken()
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
