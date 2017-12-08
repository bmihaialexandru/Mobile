package mihai.spotify2.DAL;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import mihai.spotify2.Model.SongEntity;

/**
 * Created by mihai on 12/3/2017.
 */
@Database(entities = {SongEntity.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {
    public abstract SongEntityDao dao();
}
