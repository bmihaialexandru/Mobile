package mihai.spotify2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Details extends AppCompatActivity {
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        song = (Song) intent.getSerializableExtra("song");

        EditText title = (EditText) findViewById(R.id.Name);
        title.setText(song.title);

        EditText artist = (EditText) findViewById(R.id.Artist);
        artist.setText(song.artist);
    }

    public void submitUpdate(View button){
        String title = ((EditText) findViewById(R.id.Name)).getText().toString();

        String artist = ((EditText) findViewById(R.id.Artist)).getText().toString();

        Song s=new Song(title, artist);

        SongArray.getInstance().getArray().remove(song);
        for (Song i : SongArray.getInstance().getArray()){
            if (Objects.equals(i.title, song.title) && Objects.equals(i.artist, song.artist)){
                SongArray.getInstance().getArray().remove(i);
                break;
            }
        }
        SongArray.getInstance().getArray().add(s);

        Intent i = new Intent(Details.this, SongList.class);

        startActivity(i);

        finish();


    }

}
