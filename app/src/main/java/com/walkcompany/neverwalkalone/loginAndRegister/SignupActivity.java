package com.walkcompany.neverwalkalone.loginAndRegister;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.walkcompany.neverwalkalone.MainActivity;
import com.walkcompany.neverwalkalone.R;
import com.walkcompany.neverwalkalone.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputConfiPassword,inputNom,inputPrenom,inputDate;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private RadioGroup sexeGroup;
private RadioButton sexe;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputDate = (EditText)  findViewById(R.id.DateDeNaissance);
        inputNom = (EditText) findViewById(R.id.Nom);
        inputPrenom=(EditText)findViewById(R.id.Prenom);
        inputConfiPassword=(EditText)findViewById(R.id.Confirmationpassword);
        sexeGroup = (RadioGroup) findViewById(R.id.Sexe);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        inputDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(SignupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                // get selected radio button from radioGroup
                int selectedId = sexeGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                sexe = (RadioButton) findViewById(selectedId);
                if (TextUtils.isEmpty(email)) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.email));
                    Toast.makeText(getApplicationContext(), "Entrez votre email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputNom.getText().toString())) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.Nom));
                    Toast.makeText(getApplicationContext(), "Entrez votre Nom !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputPrenom.getText().toString())) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.Prenom));
                    Toast.makeText(getApplicationContext(), "Entrez votre prenom !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputDate.getText().toString())) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.DateDeNaissance));
                    Toast.makeText(getApplicationContext(), "Entrez votre date de naissance !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!sexe.isChecked()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.Sexe));
                    Toast.makeText(getApplicationContext(), "Entrez votre sexe !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.password));
                    Toast.makeText(getApplicationContext(), "Entrez votre mot de passe !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputConfiPassword.getText().toString())) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.Confirmationpassword));
                    Toast.makeText(getApplicationContext(), "Veuillez confirmez votre mot de passe !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!inputConfiPassword.getText().toString().equals(inputPassword.getText().toString())) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.Confirmationpassword));
                    Toast.makeText(getApplicationContext(), "le mot de passe de vérifications ne correspond pas !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.password));
                    Toast.makeText(getApplicationContext(), "Mot de passe trop court entrez au moins six caractères !", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {


                                    onAuthSuccess(task.getResult().getUser());
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }
    // [START basic_write]
    private void writeNewUser(String userId, String prenom, String nom, String date,String sexe, String email) {
        User user = new User(prenom,nom,date,sexe,email);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

    private void onAuthSuccess(FirebaseUser user) {
        String username = user.getEmail();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(inputNom.getText().toString()+" "+inputPrenom.getText().toString()).build();

        user.updateProfile(profileUpdates);
        // Write new user
        writeNewUser(user.getUid(), inputPrenom.getText().toString(),inputNom.getText().toString(),inputDate.getText().toString(),sexe.getText().toString(), user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (auth.getCurrentUser() != null) {
            onAuthSuccess(auth.getCurrentUser());
        }
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        inputDate.setText(sdf.format(myCalendar.getTime()));
    }
}