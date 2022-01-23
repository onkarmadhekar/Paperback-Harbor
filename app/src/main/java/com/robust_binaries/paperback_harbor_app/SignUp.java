package com.robust_binaries.paperback_harbor_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    ImageView backBtn;
    Button register;
    TextInputLayout fullName, username, email, password, phone;
    RadioGroup radioGroup;
    RadioButton endUser;
    DatabaseReference databaseReference;
    DAOUsers daoUsers;
    DAOAuthors daoAuthors;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backBtn = findViewById(R.id.signup_back_button);
        register = findViewById(R.id.signUp_register);
        fullName = findViewById(R.id.signUp_full_name);
        username = findViewById(R.id.signUp_username);
        email = findViewById(R.id.signUp_email);
        password = findViewById(R.id.signUp_password);
        phone = findViewById(R.id.signUp_phone);
        countryCodePicker = findViewById(R.id.ccp);
        radioGroup = findViewById(R.id.radio_group);
        daoUsers = new DAOUsers();
        daoAuthors = new DAOAuthors();

        register.setOnClickListener(v -> {
            if (validateUserDetails()) {
                endUser = findViewById(radioGroup.getCheckedRadioButtonId());
                String name = fullName.getEditText().getText().toString().trim();
                String uname = username.getEditText().getText().toString().trim();
                String mail = email.getEditText().getText().toString().trim();
                String pwd = password.getEditText().getText().toString().trim();
                String phn = "+" + countryCodePicker.getFullNumber() + phone.getEditText().getText().toString().trim();
                String euser = endUser.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("fullName", name);
                bundle.putString("username", uname);
                bundle.putString("email", mail);
                bundle.putString("password", pwd);
                bundle.putString("phone", phn);
                bundle.putString("endUser", euser);

                if (euser.equals("User")) {
                    databaseReference = daoUsers.getRefToCheckIfUserExists(uname);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() == null) {
                                Intent intent = new Intent(getApplicationContext(), AuthenticateUserData.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Entered Username Already Exists !. Please Try Another Username !.", Toast.LENGTH_LONG).show();
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
                                Intent intent = new Intent(getApplicationContext(), AuthenticateUserData.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Entered Username Already Exists !. Please Try Another Username !.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }

    public void callStartUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);
        startActivity(intent);
    }

    public boolean validateUserDetails() {

        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword() | !validatePhone() | !validateEndUser()) {
            return false;
        }
        return true;
    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty!");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            username.setError("Field can not be empty!");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username maximum length should be 20!");
            return false;
        } else if (!val.matches(checkSpaces)) {
            username.setError("No white spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field can not be empty!");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        Pattern password_pattern = Pattern.compile("^" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{8,}" +
                "$");
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!password_pattern.matcher(val).matches()) {
            password.setError("Password is too weak!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        String val = phone.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            phone.setError("Field can not be empty!");
            return false;
        } else if (val.length() != 10) {
            phone.setError("Invalid Phone Number!");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEndUser() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please Select How You Want To Register As!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
