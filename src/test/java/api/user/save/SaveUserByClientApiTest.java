package api.user.save;

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
public class SaveUserByClientApiTest extends BaseApiTest {

    private static final String URI = "/users";
    private final UserCreator userCreator = new UserCreator();

    @Test
    public void saveUserByClientShouldReturnCreated() {
        UserRequestCreate user = userCreator.createUser();

        UserResponse userResponse =
                getClientWithClientToken()
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
    public void saveUserByClientWithInvalidDataShouldReturnBadRequest() {
        UserRequestCreate user = userCreator.createUserWithInvalidData();

        getClientWithClientToken()
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
