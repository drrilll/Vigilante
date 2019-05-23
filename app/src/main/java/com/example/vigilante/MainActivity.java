package com.example.vigilante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vigilante.levelEditor.LevelEditActivity;
import com.example.vigilante.levelEditor.LevelEditActivitySurface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void startLevelEditor(View view){
        Intent intent = new Intent(this, LevelEditActivitySurface.class);
        startActivity(intent);
    }

}
