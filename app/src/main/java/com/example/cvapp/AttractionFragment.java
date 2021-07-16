package com.example.cvapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;


public class AttractionFragment extends Fragment {


    public AttractionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_attraction, container, false);

        AttractionAdapter adapter = new AttractionAdapter();
        adapter.addAttraction(getString(R.string.knez_mihailova_name),
                getString(R.string.knez_mihailova_description), getString(R.string.knez_mihailova_contact));
        adapter.addAttraction(getString(R.string.contemporary_name), getString(R.string.contemporary_description), getString(R.string. contemporary_contact));
        adapter.addAttraction(getString(R.string.st_save_name), getString(R.string.st_sava_description), getString(R.string.st_sava_contact));

        RecyclerView attractionListView = view.findViewById(R.id.attractionRecyclerView);
        attractionListView.setHasFixedSize(true);
        attractionListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        attractionListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}