package com.example.forgeutils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToEgopose(View view) {
        Intent intent = new Intent(this, Egopose.class);
        startActivity(intent);
    }

    public void goToBeijing(View view) {
        Intent intent = new Intent(this, Beijing.class);
        startActivity(intent);
    }
}