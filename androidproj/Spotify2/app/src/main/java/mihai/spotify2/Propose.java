package mihai.spotify2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import mihai.spotify2.Model.SongEntity;

public class Propose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose);

        NumberPicker selectGenre = (NumberPicker)findViewById(R.id.selectGenre);
        selectGenre.setMinValue(0);
        selectGenre.setMaxValue(MainActivity.genres.size()-1);
        selectGenre.setDisplayedValues( MainActivity.genres.toArray(new String[0]));
    }

    public void submitProposal(View button){
        final String name = ((EditText) findViewById(R.id.Name)).getText().toString();

        final String artist = ((EditText) findViewById(R.id.Artist)).getText().toString();

        final String genre=Integer.toString(((NumberPicker)findViewById(R.id.selectGenre)).getValue());

        MainActivity.songs.add(new SongEntity(0,name,artist,genre));

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MainActivity.dal.add(new SongEntity(0,name,artist,genre));
                return null;
            }
        }.execute();

        boolean emailResponse = ((CheckBox) findViewById(R.id.EmailResponse)).isChecked();

        if(emailResponse) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"badila.mihaialexandru@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Song Proposal");
            i.putExtra(Intent.EXTRA_TEXT, "Song name: " + name + "\nArtist/Band name: " + artist);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Propose.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }

        Intent i = new Intent(Propose.this, SongList.class);

        startActivity(i);

        finish();

    }
}
