package com.example.cvapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("Quiz");
    }
}