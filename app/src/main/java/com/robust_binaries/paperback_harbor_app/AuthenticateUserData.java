package com.robust_binaries.paperback_harbor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AuthenticateUserData extends AppCompatActivity {
    PinView pinFromUser;
    Bundle bundle;
    String endUser;
    String phone;
    String fullName, username, email, password;
    String codeBySystem;
    DAOAuthors daoAuthors;
    DAOUsers daoUsers;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_user_data);
        bundle = getIntent().getExtras();
        daoUsers = new DAOUsers();
        daoAuthors = new DAOAuthors();
        if (bundle != null) {
            fullName = bundle.getString("fullName");
            username = bundle.getString("username");
            email = bundle.getString("email");
            password = bundle.getString("password");
            phone = bundle.getString("phone");
            endUser = bundle.getString("endUser");
        }


        pinFromUser = findViewById(R.id.pin_view);
        mAuth = FirebaseAuth.getInstance();
        sendVerificationCodeToUser(phone);
    }

    private void sendVerificationCodeToUser(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            if (endUser.equals("User")) {
                                callRegisterDataAsUser();
                                finish();
                            } else {
                                callRegisterDataAsAuthor();
                                finish();
                            }
                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "Verification Not Completed Try Again", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void callRegisterDataAsAuthor() {
        Authors author = new Authors(fullName, username, email, password, phone);
        daoAuthors.addAuthor(author).addOnSuccessListener(suc -> {
            Toast.makeText(getApplicationContext(), "Data Inserted As Author", Toast.LENGTH_LONG).show();
        }).addOnFailureListener(er ->
        {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        });

    }

    private void callRegisterDataAsUser() {
        Users user = new Users(fullName, username, email, password, phone);
        daoUsers.add(user).addOnSuccessListener(suc ->
        {
            Toast.makeText(getApplicationContext(), "Data Inserted As User", Toast.LENGTH_LONG).show();
        }).addOnFailureListener(er ->
        {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        });
    }


    public void callRegisterAsUserOrAuthor(View view) {
        String code = pinFromUser.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}