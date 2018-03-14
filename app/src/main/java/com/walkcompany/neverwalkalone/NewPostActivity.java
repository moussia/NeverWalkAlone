package com.walkcompany.neverwalkalone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.walkcompany.neverwalkalone.models.Post;
import com.walkcompany.neverwalkalone.models.User;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Champs necessaire";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]


    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;
    private Spinner spinner, spinnerSouscat, spinnerVille;
    ArrayAdapter<CharSequence> dataAdapter, dataAdapterSC, dataAdapterVille;
    private String title;
    private String sousCategorie;
    private String lieu;
    private ImageView imageSousCat;
    // ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        // categories = Category.getCategories();

        mBodyField = (EditText) findViewById(R.id.field_body);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);
        imageSousCat = (ImageView)findViewById(R.id.imageViewSousCat);

        spinner = (Spinner)findViewById(R.id.spinner_categorie);
        spinnerSouscat = (Spinner)findViewById(R.id.spinnerSouscateg);
        spinnerVille = (Spinner)findViewById(R.id.spinnerVille);

        dataAdapter = ArrayAdapter.createFromResource(this, R.array.spinnerItem, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        // attaching data adapter to spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                title = parent.getItemAtPosition(position).toString();
                if(title.equals("Sport")){
                    spinnerSouscat.setVisibility(View.VISIBLE);
                    imageSousCat.setVisibility(View.VISIBLE);
                }else{
                    spinnerSouscat.setVisibility(View.GONE);
                    imageSousCat.setVisibility(View.GONE);
                }
                if(title.equals("CATEGORIE")){

                    Toast.makeText(getApplicationContext(),"Choisissez une catégorie !", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dataAdapterSC = ArrayAdapter.createFromResource(this, R.array.spinnerItem2, android.R.layout.simple_spinner_item);
        dataAdapterSC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSouscat.setAdapter(dataAdapterSC);

        // attaching data adapter to spinner
        spinnerSouscat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sousCategorie = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dataAdapterVille = ArrayAdapter.createFromResource(this, R.array.spinnerItem3, android.R.layout.simple_spinner_item);
        dataAdapterVille.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVille.setAdapter(dataAdapterVille);

        // attaching data adapter to spinner
        spinnerVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                lieu = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        //final String title = mTitleField.getText().toString();
        //final String sousCategorie = mSousCategorieField.getText().toString();
        //final String lieu = mLieuField.getText().toString();
        final String body = mBodyField.getText().toString();


        // Categorie necessaire
    if(title.equals("CATEGORIE")){
        YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(findViewById(R.id.spinner_categorie));
        Toast.makeText(getApplicationContext(),"Veuillez choisir une catégorie.", Toast.LENGTH_SHORT).show();
        return;
    }
        if(title.equals("Soirée")){
     sousCategorie = "Soirée";
        }
        if(title.equals("Repas")){
            sousCategorie = "Repas";
        }
        if(title.equals("Trajet")){
            sousCategorie = "Trajet";
        }
       /* if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }*/


        // Lieu necessaire
        if(lieu.equals("LIEU")){
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.spinnerVille));
            Toast.makeText(getApplicationContext(),"Veuillez choisir un lieu.", Toast.LENGTH_SHORT).show();
            return;

        }
        /*

        if (TextUtils.isEmpty(lieu)) {
            mLieuField.setError(REQUIRED);
            return;
        }*/
        // Lieu necessaire
        if(sousCategorie.equals("SPORT") && title.equals("Sport")){
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.spinnerSouscateg));
            Toast.makeText(getApplicationContext(),"Veuillez choisir un sport.", Toast.LENGTH_SHORT).show();
            return;
        }
        /*
        if (TextUtils.isEmpty(sousCategorie)) {
            mSousCategorieField.setError(REQUIRED);
            return;
        }*/

        // corps message necessaire
        if (TextUtils.isEmpty(body)) {
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.field_body));
            mBodyField.setError(REQUIRED);
            Toast.makeText(getApplicationContext(),"Veuillez saisir un message pour votre post.", Toast.LENGTH_SHORT).show();
            return;
        }

        // desactive button submit pour empecher de publier plusieurs fois
        setEditingEnabled(false);
        Toast.makeText(this, "Publication en cours...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // On obtirnt l'utilisateur
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " est null");
                            Toast.makeText(NewPostActivity.this,
                                    "Impossible de trouver l'utilisateur",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Ecrit le nouveau post de l'utilisateur
                            writeNewPost(userId, user.getNom()+" "+user.getPrenom(), title, body,lieu,sousCategorie);
                        }

                        // Termine l'activité et reactive l'edition
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        //active ou non les champs titre et message du post
        mBodyField.setEnabled(enabled);
        spinner.setEnabled(enabled);
        spinnerSouscat.setEnabled(enabled);
        spinnerVille.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String body,String lieu,String sousCategorie) {
        // creer nouveau post dans  /user-posts/$userid/$postid et
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();

        Post post = new Post(userId, username, title, body,lieu,sousCategorie);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }


    // [END write_fan_out]
}
