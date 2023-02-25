package api.transfer.save;

import api.BaseApiTest;
import api.helper.creator.CarCreator;
import api.helper.creator.CarTransferCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.response.CarResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarTransferRequest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class SaveCarTransferWithClientTokenApiTest extends BaseApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();
    private final CarTransferCreator carTransferCreator = new CarTransferCreator();

    @Test
    public void saveCarTransferWithClientTokenShouldReturnCreated() {
        Long id = saveCarBeforeTest();
        CarTransferRequest carTransfer = carTransferCreator.createCarTransfer();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(carTransfer)
                .when()
                .pathParam("id", id)
                .post(URI + "/{id}/transfers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

        deleteCarAfterTest(id);
    }

    @Test
    public void saveCarTransferWithInvalidDataWithClientTokenShouldReturnBadRequest() {
        Long id = saveCarBeforeTest();
        CarTransferRequest carTransfer = carTransferCreator.createCarTransferWithInvalidData();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(carTransfer)
                .when()
                .pathParam("id", id)
                .post(URI + "/{id}/transfers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        deleteCarAfterTest(id);
    }

    @Test
    public void saveCarTransferForNonExistentCarWithClientTokenShouldReturnNotFound() {
        Long id = 123456789L;
        CarTransferRequest carTransfer = carTransferCreator.createCarTransfer();

        getClientWithClientToken()
                .contentType(ContentType.JSON)
                .body(carTransfer)
                .when()
                .pathParam("id", id)
                .post(URI + "/{id}/transfers")
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

    private void deleteCarAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
