package com.robust_binaries.paperback_harbor_app;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class DAOUsers {
    private final DatabaseReference databaseReference;

    public DAOUsers() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://paperback-harbor-app-2f430-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference(Users.class.getSimpleName());
    }

    public Task<Void> add(Users user) {
        return databaseReference.child(user.getUsername()).setValue(user);
    }

    public Query validateUserCredentials(Users user) {
        return databaseReference.orderByChild("username").equalTo(user.getUsername());
    }

    public DatabaseReference getRefToCheckIfUserExists(String username) {
        return databaseReference.child(username);
    }

}

