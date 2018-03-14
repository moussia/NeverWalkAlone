package com.walkcompany.neverwalkalone;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.walkcompany.neverwalkalone.Search.Category;


public class BaseActivity extends AppCompatActivity {
    private static String rechercheVariable="recent";
    public static String getRechercheVariable() {
        return rechercheVariable;
    }

    public static void setRechercheVariable(String rechercheVariable) {
        BaseActivity.rechercheVariable = rechercheVariable;
    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
