package api.car.delete;

import api.BaseApiTest;
import api.helper.creator.CarCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.response.CarResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class DeleteCarWithClientTokenApiTest extends BaseApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();

    @Test
    public void deleteCarWithClientTokenShouldReturnForbidden() {
        Long id = saveCarBeforeTest();

        getClientWithClientToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteCarAfterTest(id);
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

    private void deleteCarAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
