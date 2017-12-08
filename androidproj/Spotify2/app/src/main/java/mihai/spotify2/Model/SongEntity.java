package mihai.spotify2.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by mihai on 12/3/2017.
 */

@Entity
public class SongEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String artist;
    public String genre;


    public SongEntity(int id,String title,String artist, String genre) {
        this.artist = artist;
        this.title= title;
        this.id= id;
        this.genre=genre;
    }
}