package api.transfer.get;

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

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class GetCarTransfersListWithoutTokenApiTest extends BaseApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();
    private final CarTransferCreator carTransferCreator = new CarTransferCreator();

    @Test
    public void getCarTransfersListWithoutTokenShouldReturnUnauthorized() {
        Long id = saveCarBeforeTest();
        saveCarTransferBeforeTest(id);

        given()
                .when()
                .pathParam("id", id)
                .get(URI + "/{id}/transfers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

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
