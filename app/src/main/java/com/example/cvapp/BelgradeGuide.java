package com.example.cvapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import org.jetbrains.annotations.NotNull;

public class BelgradeGuide extends AppCompatActivity {

    public static boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belgrade_guide);

        Fragment attractionFragment = new AttractionFragment();
        Fragment detailFragment = new DetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        View detailFragmentTablet = findViewById(R.id.detail_fragment_tablet);
        isDualPane = detailFragmentTablet != null;

        if (isDualPane) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.attraction_fragment_tablet, attractionFragment);
            fragmentTransaction.commit();
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            fragmentTransaction1.replace(R.id.detail_fragment_tablet, detailFragment);
            fragmentTransaction1.commit();

        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, attractionFragment);
            fragmentTransaction.commit();
        }
    }
}