package commands.assembly;

import java.io.Serializable;

public class AuthOkCommandData implements Serializable {
    String userName;

    public AuthOkCommandData(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
