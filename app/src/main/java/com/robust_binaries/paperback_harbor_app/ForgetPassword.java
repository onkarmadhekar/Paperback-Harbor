package com.robust_binaries.paperback_harbor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {
    TextInputLayout usernameLout;
    Button forgetPasswordSubmitBtn;
    RadioGroup radioGroup;
    RadioButton accType;
    DAOUsers daoUsers;
    DAOAuthors daoAuthors;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        usernameLout = findViewById(R.id.forget_password_username);
        radioGroup = findViewById(R.id.forget_pwd_radio_group);
        forgetPasswordSubmitBtn = findViewById(R.id.forget_password_submit_btn);
        daoUsers = new DAOUsers();
        daoAuthors = new DAOAuthors();
        forgetPasswordSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUserDetails()) {
                    String uname = usernameLout.getEditText().getText().toString().trim();
                    accType = findViewById(radioGroup.getCheckedRadioButtonId());
                    String accountType = accType.getText().toString();
                    if (accountType.equals("User")) {
                        databaseReference = daoUsers.getRefToCheckIfUserExists(uname);
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.getValue() == null) {
                                    Toast.makeText(getApplicationContext(), "No Such User Exists!", Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), ForgetPasswordCheckOTP.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", uname);
                                    bundle.putString("accountType", accountType);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        databaseReference = daoAuthors.getRefToCheckIfAuthorExists(uname);
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.getValue() == null) {
                                    Toast.makeText(getApplicationContext(), "No Such User Exists!", Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), ForgetPasswordCheckOTP.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", uname);
                                    bundle.putString("accountType", accountType);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });
    }

    private boolean validateUserDetails() {
        if (!validateUsername() | !validateEndUser()) {
            return false;
        }
        return true;
    }

    private boolean validateEndUser() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please Select Your Account Type !", Toast.LENGTH_LONG).show();
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

    public void callStartUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

}