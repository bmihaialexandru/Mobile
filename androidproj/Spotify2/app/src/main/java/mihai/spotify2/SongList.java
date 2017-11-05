package mihai.spotify2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

public class SongList extends AppCompatActivity {

    static final int UPDATE_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        final ListView listView=(ListView)findViewById(R.id.List);

        ArrayList<Song> songs = SongArray.getInstance().getArray();

        final CustomAdapter adapter= new CustomAdapter(this, songs);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Song song= (Song)o;

                Intent i = new Intent(SongList.this, Details.class);

                i.putExtra("song", song);

                startActivity(i);

                finish();
            }
        });

    }

    public void proposeSong(View button){
        Intent i = new Intent(SongList.this, Propose.class);

        startActivity(i);

    }
}
