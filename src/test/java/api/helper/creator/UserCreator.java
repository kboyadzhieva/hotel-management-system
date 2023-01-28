package api.helper.creator;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestCreate;

public class UserCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public UserRequestCreate createUser() {
        String content = "classpath:createUser.json";
        return objectCreator.createObject(content, UserRequestCreate.class);
    }

    public UserRequestCreate createUserByAdmin() {
        String content = "classpath:createUserByAdmin.json";
        return objectCreator.createObject(content, UserRequestCreate.class);
    }

    public UserRequestCreate createUserWithInvalidData() {
        String content = "classpath:createUserWithInvalidData.json";
        return objectCreator.createObject(content, UserRequestCreate.class);
    }
}
