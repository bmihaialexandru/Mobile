package mihai.spotify2.DAL;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mihai.spotify2.Model.SongEntity;

/**
 * Created by mihai on 12/3/2017.
 */
@Dao
public interface SongEntityDao {

    @Insert
    void insertOnlySingleRecord(SongEntity song);

    @Query("SELECT * FROM SongEntity")
    List<SongEntity> fetchAllData();

    @Query("SELECT * FROM SongEntity WHERE id=:i")
    SongEntity getSingleRecord(int i);


    @Query("SELECT * FROM SongEntity WHERE title=:t AND artist=:a")
    SongEntity getRecord(String t, String a);

    @Update
    void updateRecord(SongEntity song);

    @Delete
    void deleteRecord(SongEntity song);
}
