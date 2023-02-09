package api.car.save;

import api.BaseApiTest;
import api.helper.creator.CarCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.car.request.CarRequest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class SaveCarWithClientTokenApiTest extends BaseApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();

    @Test
    public void saveCarWithClientTokenShouldReturnForbidden() {
        CarRequest car = carCreator.createCar();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
