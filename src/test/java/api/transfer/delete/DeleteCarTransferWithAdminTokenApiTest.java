package api.transfer.delete;

import api.BaseApiTest;
import api.helper.creator.CarCreator;
import api.helper.creator.CarTransferCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.response.CarResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarTransferRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.response.CarTransferResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class DeleteCarTransferWithAdminTokenApiTest extends BaseApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();
    private final CarTransferCreator carTransferCreator = new CarTransferCreator();

    @Test
    public void deleteCarTransferWithAdminTokenShouldReturnNoContent() {
        Long id = saveCarBeforeTest();
        Long tid = saveCarTransferBeforeTest(id);

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .pathParam("tid", tid)
                .delete(URI + "/{id}/transfers/{tid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());

        deleteCarAfterTest(id);
    }

    @Test
    public void deleteCarTransferByNonExistentIdWithAdminTokenShouldReturnNotFound() {
        Long id = saveCarBeforeTest();
        Long tid = 123456789L;

        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .pathParam("tid", tid)
                .delete(URI + "/{id}/transfers/{tid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

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

    private Long saveCarTransferBeforeTest(Long id) {
        CarTransferRequest carTransfer = carTransferCreator.createCarTransfer();

        CarTransferResponse carTransferResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(carTransfer)
                        .when()
                        .pathParam("id", id)
                        .post(URI + "/{id}/transfers")
                        .then()
                        .extract()
                        .as(CarTransferResponse.class);

        return carTransferResponse.getId();
    }

    private void deleteCarAfterTest(Long id) {
        getClientWithAdminToken()
                .when()
                .pathParam("id", id)
                .delete(URI + "/{id}");
    }
}
