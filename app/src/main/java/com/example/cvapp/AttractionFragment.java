package com.example.cvapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;


public class AttractionFragment extends Fragment implements AttractionAdapter.ItemClickListener {

    AttractionFragmentListener attractionFragmentListener;

public interface AttractionFragmentListener {
    void onInputSent(Attraction currentAttraction);
    }

    public AttractionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attraction, container, false);

        AttractionAdapter adapter = new AttractionAdapter(this);
        adapter.addAttraction(getString(R.string.knez_mihailova_name),
                getString(R.string.knez_mihailova_description), getString(R.string.knez_mihailova_contact), R.drawable.knez_mihailova);
        adapter.addAttraction(getString(R.string.contemporary_name), getString(R.string.contemporary_description), getString(R.string.contemporary_contact), R.drawable.contemporary_art);
        adapter.addAttraction(getString(R.string.st_save_name), getString(R.string.st_sava_description), getString(R.string.st_sava_contact), R.drawable.st_sava);
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

    @Override
    public void onItemClick(Attraction attraction) {
    Attraction currentAttraction = attraction;
    attractionFragmentListener.onInputSent(attraction);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof AttractionFragmentListener) {
            attractionFragmentListener = (AttractionFragmentListener) getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AttractionFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        attractionFragmentListener = null;
    }
}