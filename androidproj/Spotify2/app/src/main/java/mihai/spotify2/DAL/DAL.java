package mihai.spotify2.DAL;

import java.util.List;

import mihai.spotify2.Model.SongEntity;

/**
 * Created by mihai on 12/3/2017.
 */

public class DAL {
    private SongDatabase database;

    public DAL(SongDatabase database) {
        this.database = database;
    }

    public List<SongEntity> getList() {
        return database.dao().fetchAllData();
    }

    public SongEntity getSong(String title, String artist) {
        return database.dao().getRecord(title, artist);
    }

    public void add(SongEntity s) {
        database.dao().insertOnlySingleRecord(s);
    }

    public void delete(SongEntity s) {
        database.dao().deleteRecord(s);
    }

    public void update(SongEntity s) {
        database.dao().updateRecord(s);
    }
}
