package mihai.spotify2;

import java.io.Serializable;

/**
 * Created by mihai on 11/5/2017.
 */

public class Song implements Serializable {
    public String title;
    public String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
}
