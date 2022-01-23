package com.robust_binaries.paperback_harbor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnUserView;
    Button btnPublisherView;
    Button btnAdminView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUserView = findViewById(R.id.btnUserView);
        btnUserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BookCard.class);
                startActivity(intent);
            }
        });

        btnPublisherView = findViewById(R.id.btnPublisherView);
        btnPublisherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PublisherDashboard.class);
                startActivity(intent);
            }
        });

     




    }


}