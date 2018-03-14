package com.walkcompany.neverwalkalone.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostsFragment extends PostListFragment {

    public MyPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // recupere tout les post de user
        return databaseReference.child("user-posts")
                .child(getUid());
    }
}
