package com.walkcompany.neverwalkalone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.walkcompany.neverwalkalone.loginAndRegister.LoginActivity;

public class VerificationActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (auth.getCurrentUser() != null) {
            // Check auth on Activity start
//verif();

        }
        setContentView(R.layout.activity_verification);



        Button btnRenvoie = (Button) findViewById(R.id.renvoi);
        btnRenvoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    // Check auth on Activity start
                    auth.getCurrentUser().sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                       verif();
                                    }
                                }
                            });




                }
            }
        });
        Button btnValide = (Button) findViewById(R.id.valide);
        btnRenvoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    // Check auth on Activity start

                       verif();


                }
            }
        });

    }

    private void verif() {
        if (auth.getCurrentUser()!= null) {


            if (auth.getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(VerificationActivity.this, MainActivity.class));
                finish();
            }

            else {

               /* startActivity(new Intent(VerificationActivity.this, VerificationActivity.class));
                finish();*/

            }

        }

        }



}
