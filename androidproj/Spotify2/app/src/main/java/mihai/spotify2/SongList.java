package mihai.spotify2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mihai.spotify2.DAL.DAL;
import mihai.spotify2.DAL.SongDatabase;
import mihai.spotify2.Model.SongEntity;

public class SongList extends AppCompatActivity {

    static final int UPDATE_CODE = 0;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Button proposeButton = (Button) findViewById(R.id.ProposeButton);
        FirebaseDatabase.getInstance().getReference("users").child(uid).child("premiumUser")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean isPremium = dataSnapshot.getValue(Boolean.class);

                        if (!isPremium)
                            proposeButton.setEnabled(false);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        final ListView listView=(ListView)findViewById(R.id.List);

        List<SongEntity> songs = MainActivity.songs;

        final CustomAdapter adapter= new CustomAdapter(this, songs);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                SongEntity song= (SongEntity)o;

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

        finish();

    }
}
