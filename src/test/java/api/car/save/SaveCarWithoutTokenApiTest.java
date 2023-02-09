package api.car.save;

import api.helper.creator.CarCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.car.request.CarRequest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class SaveCarWithoutTokenApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();

    @Test
    public void saveCarWithoutTokenShouldReturnUnauthorized() {
        CarRequest car = carCreator.createCar();

        given()
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
