package mihai.spotify2;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mihai.spotify2.DAL.DAL;
import mihai.spotify2.DAL.SongDatabase;
import mihai.spotify2.Model.SongEntity;

public class MainActivity extends AppCompatActivity {
    static SongDatabase database;
    static List<SongEntity> songs;
    static List<String> genres;
    static DAL dal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.database = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, "database1").build();
        MainActivity.dal = new DAL(MainActivity.database);
        MainActivity.genres = new ArrayList<String>(Arrays.asList("Rock", "Pop","Techno","Rap"));

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MainActivity.songs = MainActivity.dal.getList();
                return null;
            }
        }.execute();
    }

    public void startApp(View button){

        Intent i = new Intent(MainActivity.this, SongList.class);

        startActivity(i);

    }
}
