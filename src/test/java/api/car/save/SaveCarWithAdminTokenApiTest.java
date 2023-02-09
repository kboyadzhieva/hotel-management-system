package api.car.save;

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
public class SaveCarWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();

    @Test
    public void saveCarWithAdminTokenShouldReturnCreated() {
        CarRequest car = carCreator.createCar();

        CarResponse carResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(car)
                        .when()
                        .post(URI)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(CarResponse.class);

        deleteCarAfterTest(carResponse.getId());
    }

    @Test
    public void saveCarWithInvalidDataWithAdminTokenShouldReturnBadRequest() {
        CarRequest car = carCreator.createCarWithInvalidData();

        getClientWithAdminToken()
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void deleteCarAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
