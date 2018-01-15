package mihai.spotify2;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mihai.spotify2.DAL.DAL;
import mihai.spotify2.DAL.SongDatabase;
import mihai.spotify2.Model.SongEntity;
import mihai.spotify2.Model.User;

public class MainActivity extends AppCompatActivity {
    static SongDatabase database;
    static List<SongEntity> songs;
    static List<String> genres;
    static DAL dal;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private void redirectToList(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, SongList.class);

            Intent intentInitial = getIntent();
            if (intentInitial != null) {
                User user = (User) intentInitial.getSerializableExtra("userData");
                intent.putExtra("userData", user);
            }

            startActivity(intent);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        MainActivity.database = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, "database5").build();
        MainActivity.dal = new DAL(MainActivity.database);
        MainActivity.genres = new ArrayList<String>(Arrays.asList("Rock", "Pop","Techno","Rap"));

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MainActivity.songs = MainActivity.dal.getList();
                return null;
            }
        }.execute();
        auth = FirebaseAuth.getInstance();
    }

    public void loginUser(View button) {
        final String userName = ((EditText) findViewById(R.id.UserName)).getText().toString();

        final String password = ((EditText) findViewById(R.id.Password)).getText().toString();

        auth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            redirectToList(user);

                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void registerUser(View view) {
        Intent i = new Intent(MainActivity.this, RegisterActivity.class);

        startActivity(i);
    }
}
