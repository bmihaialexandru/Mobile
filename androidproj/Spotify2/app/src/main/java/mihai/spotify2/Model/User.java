package mihai.spotify2.Model;

import java.io.Serializable;

/**
 * Created by mihai on 1/13/2018.
 */

public class User implements Serializable {
    public boolean premiumUser;
    public String uid;
    public String userName;

    public User( boolean premiumUser, String uid, String userName) {

        this.premiumUser = premiumUser;
        this.uid = uid;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                ", premiumUser=" + premiumUser +
                ", uid='" + uid + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}