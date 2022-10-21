package api;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

    private final JwtGenerator jwtGenerator = new JwtGenerator();

    public RequestSpecification getClientWithAdminToken() {
        String token = jwtGenerator.generateTokenAdmin();
        return given().header("Authorization", "Bearer " + token);
    }

    public RequestSpecification getClientWithClientToken() {
        String token = jwtGenerator.generateTokenClient();
        return given().header("Authorization", "Bearer " + token);
                
    }
}
