package com.resuadam.tema1ejer5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast msg = new Toast(this);
        msg.makeText(this, "Hi! I am onCreate()!", Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "Hi! ...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast msg = new Toast(this);
        msg.makeText(this, "Hi! I am onStart()!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast msg = new Toast(this);
        msg.makeText(this, "Hi! I am onResume()!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Toast msg = new Toast(this);
        msg.makeText(this, "Hi! I am onPause()!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Toast msg = new Toast(this);
        msg.makeText(this, "Hi! I am onStop()!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast msg = new Toast(this);
        msg.makeText(this, "Hi! I am onDestroy()!", Toast.LENGTH_SHORT).show();
    }
}