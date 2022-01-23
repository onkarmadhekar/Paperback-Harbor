package com.robust_binaries.paperback_harbor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

public class SetNewPassword extends AppCompatActivity {
    TextInputLayout newPasswordLout, confirmPasswordLout;
    Button submitBtn;
    Bundle bundle;
    String uname, accType;
    DAOUsers daoUsers;
    DAOAuthors daoAuthors;
    String newPassword;
    String confirmPassword;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        newPasswordLout = findViewById(R.id.reset_new_password);
        confirmPasswordLout = findViewById(R.id.reset_confirm_password);
        daoAuthors = new DAOAuthors();
        daoUsers = new DAOUsers();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            uname = bundle.getString("username");
            accType = bundle.getString("accountType");
        }
        submitBtn = findViewById(R.id.reset_password_submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUserDetails()) {
                    newPassword = newPasswordLout.getEditText().getText().toString().trim();
                    confirmPassword = confirmPasswordLout.getEditText().getText().toString().trim();
                    if (accType.equals("User"))
                        updatePasswordInUserDb(uname, newPassword);
                    else
                        updatePasswordInAuthorDb(uname, newPassword);
                    Intent intent = new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void updatePasswordInAuthorDb(String uname, String newPassword) {
        databaseReference = daoAuthors.getRefToCheckIfAuthorExists(uname);
        databaseReference.child("password").setValue(newPassword);
    }

    private void updatePasswordInUserDb(String uname, String newPassword) {
        databaseReference = daoUsers.getRefToCheckIfUserExists(uname);
        databaseReference.child("password").setValue(newPassword);
    }

    private boolean validateUserDetails() {
        if (!validateNewPassword() | !checkNewPasswordAndConfirmPasswordSame()) {
            return false;
        }
        return true;
    }

    private boolean checkNewPasswordAndConfirmPasswordSame() {
        String val1 = newPasswordLout.getEditText().getText().toString().trim();
        String val2 = confirmPasswordLout.getEditText().getText().toString().trim();
        if (val1.equals(val2))
            return true;
        else {
            Toast.makeText(getApplicationContext(), "NewPassword and Confirm Password Text Does Not Match !", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validateNewPassword() {
        String val = newPasswordLout.getEditText().getText().toString().trim();
        Pattern password_pattern = Pattern.compile("^" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{8,}" +
                "$");
        if (val.isEmpty()) {
            newPasswordLout.setError("Field can not be empty");
            return false;
        } else if (!password_pattern.matcher(val).matches()) {
            newPasswordLout.setError("Password is too weak!");
            return false;
        } else {
            newPasswordLout.setError(null);
            newPasswordLout.setErrorEnabled(false);
            return true;
        }
    }
}