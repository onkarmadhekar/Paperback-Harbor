package com.robust_binaries.paperback_harbor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class PublisherDashboard extends AppCompatActivity {

    Button btnViewBooks;
    Button btnPublishContent;
    Button btnAdmin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_dashboard);

        btnViewBooks = findViewById(R.id.btnViewBooks);
        btnViewBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublisherDashboard.this, BookCard.class);
                startActivity(intent);

            }
        });

        btnPublishContent = findViewById(R.id.btnPublishContent);
        btnPublishContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublisherDashboard.this,PublisherContent.class);
                startActivity(intent);
            }
        });

        btnAdmin1 = findViewById(R.id.btnAdmin1);
        btnAdmin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublisherDashboard.this,Admin.class);
                startActivity(intent);
            }
        });
    }


}