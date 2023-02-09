package api.car.delete;

import api.BaseApiTest;
import api.helper.creator.CarCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.car.request.CarRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.car.response.CarResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class DeleteCarWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();

    @Test
    public void deleteCarWithAdminTokenShouldReturnNoContent() {
        Long id = saveCarBeforeTest();

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteCarByNonExistentIdWithAdminTokenShouldReturnNotFound() {
        Long id = 123456789000L;

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private Long saveCarBeforeTest() {
        CarRequest car = carCreator.createCar();

        CarResponse carResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(car)
                        .when()
                        .post(URI)
                        .then()
                        .extract()
                        .as(CarResponse.class);

        return carResponse.getId();
    }
}
