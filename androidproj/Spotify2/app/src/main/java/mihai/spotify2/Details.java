package mihai.spotify2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import mihai.spotify2.Model.SongEntity;

public class Details extends AppCompatActivity {
    SongEntity song;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference songsReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        song = (SongEntity) intent.getSerializableExtra("song");

        EditText title = (EditText) findViewById(R.id.Name);
        title.setText(song.title);

        EditText artist = (EditText) findViewById(R.id.Artist);
        artist.setText(song.artist);

        NumberPicker selectGenre = (NumberPicker)findViewById(R.id.selectGenre);
        selectGenre.setMinValue(0);
        selectGenre.setMaxValue(MainActivity.genres.size()-1);
        selectGenre.setDisplayedValues( MainActivity.genres.toArray(new String[0]));
        selectGenre.setValue(Integer.parseInt(song.genre));



        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        ArrayList<PieEntry> entries=new ArrayList<>();

        int ct=0;
        for(SongEntity s:MainActivity.songs){
            if(Objects.equals(s.artist, song.artist)){
                ct+=1;
            }
        }
        entries.add(new PieEntry(ct,song.artist));
        entries.add(new PieEntry(MainActivity.songs.size()-ct,"Other"));

        PieDataSet dataset = new PieDataSet(entries, "");

        ArrayList<String> labels=new ArrayList<>();
        labels.add(song.artist);
        labels.add("Other Artists");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData=new PieData(dataset);
        Description description=new Description();
        description.setText("# of songs from this artist");
        pieChart.setDescription(description);
        pieChart.setData(pieData);

    }

    public void submitUpdate(View button){
        final String title = ((EditText) findViewById(R.id.Name)).getText().toString();

        final String artist = ((EditText) findViewById(R.id.Artist)).getText().toString();

        final String genre=Integer.toString(((NumberPicker)findViewById(R.id.selectGenre)).getValue());

        final SongEntity s=new SongEntity(0,title, artist,genre, song.fireKey);

        for (SongEntity i : MainActivity.songs){
            if (Objects.equals(i.title, song.title) && Objects.equals(i.artist, song.artist)){
                MainActivity.songs.remove(i);
                break;
            }
        }


        MainActivity.songs.add(s);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                SongEntity newSong=MainActivity.dal.getSong(song.title,song.artist);
                newSong.title=title;
                newSong.artist=artist;
                newSong.genre=genre;
                MainActivity.dal.update(newSong);
                return null;
            }
        }.execute();


        songsReference = firebaseDatabase.getReference("songs").child(s.fireKey);
        songsReference.setValue(s);

        Intent i = new Intent(Details.this, SongList.class);

        startActivity(i);

        finish();
    }

    public void deleteSong(View button){
        final String title = ((EditText) findViewById(R.id.Name)).getText().toString();

        final String artist = ((EditText) findViewById(R.id.Artist)).getText().toString();



        AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
        builder.setMessage("Are you sure you want to delete this song?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                for (SongEntity i : MainActivity.songs){
                    if (Objects.equals(i.title, song.title) && Objects.equals(i.artist, song.artist)){

                        songsReference = firebaseDatabase.getReference("songs").child(i.fireKey);
                        songsReference.setValue(null);

                        MainActivity.songs.remove(i);
                        break;
                    }
                }

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        MainActivity.dal.delete(MainActivity.dal.getSong(song.title,song.artist));

                        return null;
                    }
                }.execute();


                Toast.makeText(Details.this, "Deleted succesfully!", Toast.LENGTH_LONG).show();

                Intent i = new Intent(Details.this, SongList.class);

                startActivity(i);

                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

}
