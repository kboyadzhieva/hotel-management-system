package api.user.updater;

import api.ObjectCreator;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestUpdate;

public class UserUpdater {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public UserRequestUpdate updateUser() {
        String content = "classpath:createUser.json";
        return objectCreator.createObject(content, UserRequestUpdate.class);
    }

    public UserRequestUpdate updateUserByAdmin() {
        String content = "classpath:updateUserByAdmin.json";
        return objectCreator.createObject(content, UserRequestUpdate.class);
    }

    public UserRequestUpdate updateUserByAdminWithInvalidData() {
        String content = "classpath:updateUserByAdminWithInvalidData.json";
        return objectCreator.createObject(content, UserRequestUpdate.class);
    }
}
