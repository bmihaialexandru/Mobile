package mihai.spotify2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Propose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose);
    }

    public void submitProposal(View button){
        String name = ((EditText) findViewById(R.id.Name)).getText().toString();

        String artist = ((EditText) findViewById(R.id.Artist)).getText().toString();

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
        finish();
    }
}
