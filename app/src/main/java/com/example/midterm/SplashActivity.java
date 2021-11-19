package com.example.midterm;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Splash screen activity that shows when application is first started. When HomeActivity
 * is ready to be started, SplashActivity is dismissed and HomeActivity is displayed
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }
}
