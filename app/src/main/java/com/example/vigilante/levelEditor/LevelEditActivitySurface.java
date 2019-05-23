package com.example.vigilante.levelEditor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LevelEditActivitySurface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LevelEditView(this));
    }
}
