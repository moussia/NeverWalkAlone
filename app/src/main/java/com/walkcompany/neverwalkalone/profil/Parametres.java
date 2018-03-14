package com.walkcompany.neverwalkalone.profil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.walkcompany.neverwalkalone.BaseActivity;
import com.walkcompany.neverwalkalone.R;
import com.walkcompany.neverwalkalone.models.User;

public class Parametres extends BaseActivity {
    private Button boutonMdp;
    private Button boutonEnregistrer;
    private DatabaseReference mDatabase;
    private final String userId = getUid();
    private TextView nom;
    private TextView prenom;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        this.nom = (TextView) findViewById((R.id.nom));
        this.prenom = (TextView) findViewById(R.id.prenom);
        this.date = (TextView) findViewById(R.id.dateNaiss);

        this.boutonMdp = (Button) findViewById(R.id.boutonmdp);
        this.boutonEnregistrer = (Button) findViewById((R.id.enregistrer));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d("TAG", "nom " + user.getNom());
                Log.d("TAG", "prenom " + user.getPrenom());
                Log.d("TAG", "date " + user.getDateDeNaissance());
                nom.setText(user.getNom());
                prenom.setText(user.getPrenom());
                date.setText(user.getDateDeNaissance());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });


        boutonMdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parametres.this, ModifierMdp.class);
                startActivity(intent);
            }
        });



        boutonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rempli()){
                    enregistrerProfil();
                    Intent intent = new Intent(Parametres.this, GestionProfil.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean rempli() {
        this.nom = (TextView) findViewById((R.id.nom));
        this.prenom = (TextView) findViewById(R.id.prenom);
        this.date = (TextView) findViewById(R.id.dateNaiss);
        boolean rempli = true;

        if (TextUtils.isEmpty(this.nom.getText().toString())) {
            YoYo.with(Techniques.Shake).duration(700).playOn(this.nom);
            this.nom.setError("Veuillez entrer un nom svp");
            rempli = false;
        }
        if (TextUtils.isEmpty(this.prenom.getText().toString())) {
            YoYo.with(Techniques.Shake).duration(700).playOn(this.prenom);
            this.prenom.setError("Veuillez entrer un prénom svp");
            rempli = false;
        }
        if (TextUtils.isEmpty(this.date.getText().toString())) {
            YoYo.with(Techniques.Shake).duration(700).playOn(this.date);
            this.date.setError("Erreur, veuillez une date valide svp");
            rempli = false;
        }
        return rempli;
    }

    private void enregistrerProfil(){
        mDatabase.child("users").child(userId).child("nom").setValue(this.nom.getText().toString());
        mDatabase.child("users").child(userId).child("prenom").setValue(this.prenom.getText().toString());
        mDatabase.child("users").child(userId).child("dateDeNaissance").setValue(this.date.getText().toString());

    }
}
