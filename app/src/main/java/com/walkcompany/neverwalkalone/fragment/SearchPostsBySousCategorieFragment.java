package com.walkcompany.neverwalkalone.fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.walkcompany.neverwalkalone.BaseActivity;
import com.walkcompany.neverwalkalone.Search.Category;

import java.util.ArrayList;

public class SearchPostsBySousCategorieFragment extends PostListFragment {

    public SearchPostsBySousCategorieFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        Query q = null;
if(!BaseActivity.getRechercheVariable().equals("recent")){
    q = databaseReference.child("posts").orderByChild("sousCategorie").equalTo(BaseActivity.getRechercheVariable());
}else if(BaseActivity.getRechercheVariable().contains("sports")){
    q = databaseReference.child("posts").orderByChild("title").equalTo("Sport");
        }
else if(BaseActivity.getRechercheVariable().contains("Soirées")){
    q = databaseReference.child("posts").orderByChild("title").equalTo("Soirée");
}else if(BaseActivity.getRechercheVariable().contains("Repas")){
    q = databaseReference.child("posts").orderByChild("title").equalTo("Repas");
}
else {
    q = databaseReference.child("posts").limitToFirst(100);
}
return q ;
    }
}
