package com.walkcompany.neverwalkalone.profil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.walkcompany.neverwalkalone.BaseActivity;
import com.walkcompany.neverwalkalone.MainActivity;
import com.walkcompany.neverwalkalone.R;
import com.walkcompany.neverwalkalone.loginAndRegister.LoginActivity;
import com.walkcompany.neverwalkalone.models.User;

public class GestionProfil extends BaseActivity{
    private ImageButton preference;
    private DatabaseReference mDatabase;
    private final String userId = getUid();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        this.preference = (ImageButton) findViewById(R.id.parametre);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView nom = (TextView) findViewById((R.id.Nom));
                TextView prenom = (TextView) findViewById(R.id.Prenom);
                // image = (ImageView) findViewById(R.id.photo);
                User user = dataSnapshot.getValue(User.class);

                nom.setText(user.getNom());
                prenom.setText(user.getPrenom());
                //image
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });

        this.preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GestionProfil.this, Parametres.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return true;
        }else if(i== R.id.accueil) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        else if(i== R.id.profil){
            startActivity(new Intent(this, GestionProfil.class));
            return true;
        }else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}