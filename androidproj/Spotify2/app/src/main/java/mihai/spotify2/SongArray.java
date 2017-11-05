package mihai.spotify2;

import java.util.ArrayList;

/**
 * Created by mihai on 11/5/2017.
 */

public class SongArray  {

    private static SongArray mInstance;
    private ArrayList<Song> list = null;

    public static SongArray getInstance() {
        if(mInstance == null)
            mInstance = new SongArray();

        return mInstance;
    }

    private SongArray() {
        list = new ArrayList<Song>();
        list.add(new Song("a","a"));
        list.add(new Song("b","b"));
        list.add(new Song("c","c"));
    }
    // retrieve array from anywhere
    public ArrayList<Song> getArray() {
        return this.list;
    }
    //Add element to array
    public void addToArray(Song value) {
        list.add(value);
    }
}