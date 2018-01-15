package mihai.spotify2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mihai.spotify2.Model.User;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference songsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
    }

    public void registerUser(View button){

        final String userName = ((EditText) findViewById(R.id.UserName)).getText().toString();

        final String password = ((EditText) findViewById(R.id.Password)).getText().toString();

        final boolean premiumUser = ((CheckBox) findViewById(R.id.PremiumUser)).isChecked();

        auth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            songsReference = firebaseDatabase.getReference("users");
                            FirebaseUser currentUser = auth.getCurrentUser();
                            String uid = currentUser.getUid();
                            String email1 = currentUser.getEmail();
                            User myUser = new User( premiumUser, uid, email1);
                            songsReference.child(uid).setValue(myUser);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
    }
}
