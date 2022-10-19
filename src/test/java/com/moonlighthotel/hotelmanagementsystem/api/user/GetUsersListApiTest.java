package com.moonlighthotel.hotelmanagementsystem.api.user;

import com.moonlighthotel.hotelmanagementsystem.api.BaseApiTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class GetUsersListApiTest extends BaseApiTest {

    private static final String URI = "/users";

    @Test
    public void getUsersListWithoutTokenShouldReturnUnauthorized() {
        given()
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getUsersListWithAdminTokenShouldReturnOK() {
        getClientWithAdminToken()
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getUsersListWithClientTokenShouldReturnForbidden() {
        getClientWithClientToken()
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
