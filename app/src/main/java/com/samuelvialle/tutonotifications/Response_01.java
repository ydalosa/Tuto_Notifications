package com.samuelvialle.tutonotifications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Response_01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response01);

        String message = getIntent().getStringExtra("message");
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }
}