package com.robust_binaries.paperback_harbor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    TextInputLayout usernameLout, passwordLout;
    String username, password;
    RadioGroup radioGroup;
    RadioButton endUser;
    Button loginBtn;
    DAOUsers daoUsers;
    DAOAuthors daoAuthors;
    String superUser;
    String superUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        daoUsers = new DAOUsers();
        daoAuthors = new DAOAuthors();
        superUser = "admin";
        superUserPassword = "Admin@1234";
        usernameLout = findViewById(R.id.login_username);
        passwordLout = findViewById(R.id.login_password);
        radioGroup = findViewById(R.id.login_radio_group);
        loginBtn = findViewById(R.id.login_scrren_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (validateUserDetails()) {
                    endUser = findViewById(radioGroup.getCheckedRadioButtonId());
                    String euser = endUser.getText().toString();
                    username = usernameLout.getEditText().getText().toString().trim();
                    password = passwordLout.getEditText().getText().toString().trim();

                    if (euser.equals("User")) {
                        Users user = new Users(username);
                        Query checkUser = daoUsers.validateUserCredentials(user);
                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    usernameLout.setError(null);
                                    usernameLout.setErrorEnabled(false);

                                    String systemPassword = snapshot.child(username).child("password").getValue(String.class);
                                    if (systemPassword.equals(password)) {
                                        passwordLout.setError(null);
                                        passwordLout.setErrorEnabled(false);
                                        Toast.makeText(getApplicationContext(), "Login Successful As User!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Login.this,BookCard.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password Does Not Match !", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "No Such User Exists !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    } else {
                        Authors author = new Authors(username);
                        Query checkAuthor = daoAuthors.validateAuthorCredentials(author);
                        checkAuthor.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    usernameLout.setError(null);
                                    usernameLout.setErrorEnabled(false);

                                    String systemPassword = snapshot.child(username).child("password").getValue(String.class);
                                    if (systemPassword.equals(password)) {
                                        passwordLout.setError(null);
                                        passwordLout.setErrorEnabled(false);
                                        Toast.makeText(getApplicationContext(), "Login Successful As Author!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Login.this,PublisherDashboard.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password Does Not Match !", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "No Such User Exists !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean validateUserDetails() {

        if (!validateUsername() | !validatePassword() | !validateEndUser()) {
            return false;
        }
        return true;
    }

    private boolean validateEndUser() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please Select How You Want To Login As!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validateUsername() {
        String val = usernameLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            usernameLout.setError("Field can not be empty!");
            return false;
        } else {
            usernameLout.setError(null);
            usernameLout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = passwordLout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            passwordLout.setError("Field can not be empty");
            return false;
        } else {
            passwordLout.setError(null);
            passwordLout.setErrorEnabled(false);
            return true;
        }
    }

    public void callStartUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.forget_back_button), "transition_startUpScreen");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, options.toBundle());
        startActivity(intent);
    }

    public void callForgetPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }
}