package api;

import com.moonlighthotel.hotelmanagementsystem.security.jwt.JwtAuthenticationRequest;
import com.moonlighthotel.hotelmanagementsystem.security.jwt.JwtAuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JwtGenerator {

    private static final String TOKEN_URL = "http://localhost:8080/users/token";
    private static final JwtAuthenticationRequest ADMIN_USER = new AuthenticationRequestCreator().createAdminRequest();
    private static final JwtAuthenticationRequest CLIENT_USER = new AuthenticationRequestCreator().createClientRequest();

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateTokenAdmin() {
        ResponseEntity<JwtAuthenticationResponse> authenticationResponseResponseEntity
                = restTemplate.postForEntity(TOKEN_URL, ADMIN_USER, JwtAuthenticationResponse.class);

        return authenticationResponseResponseEntity.getBody().getToken();
    }

    public String generateTokenClient() {
        ResponseEntity<JwtAuthenticationResponse> authenticationResponseResponseEntity
                = restTemplate.postForEntity(TOKEN_URL, CLIENT_USER, JwtAuthenticationResponse.class);

        return authenticationResponseResponseEntity.getBody().getToken();
    }
}
