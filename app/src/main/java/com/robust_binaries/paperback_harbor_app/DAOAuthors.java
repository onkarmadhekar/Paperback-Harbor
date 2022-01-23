package com.robust_binaries.paperback_harbor_app;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class DAOAuthors {
    private final DatabaseReference databaseReference;

    public DAOAuthors() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://paperback-harbor-app-2f430-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference(Authors.class.getSimpleName());
    }

    public Task<Void> addAuthor(Authors author) {
        return databaseReference.child(author.getUsername()).setValue(author);
    }

    public Query validateAuthorCredentials(Authors author) {
        return databaseReference.orderByChild("username").equalTo(author.getUsername());
    }

    public DatabaseReference getRefToCheckIfAuthorExists(String username) {
        return databaseReference.child(username);
    }

}
