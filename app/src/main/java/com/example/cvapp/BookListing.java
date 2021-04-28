package com.example.cvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cvapp.MainActivity;
import com.example.cvapp.R;

import static java.security.AccessController.getContext;

public class BookListing extends MainActivity {

    private BookSearhViewModel bookSearhViewModel;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);

        adapter = new BookAdapter();
        RecyclerView recyclerView = findViewById(R.id.book_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        bookSearhViewModel = new ViewModelProvider(this).get(BookSearhViewModel.class);
        bookSearhViewModel.init();
        bookSearhViewModel.getVolumesResponseLiveData().observe(this, new Observer<VolumesResponse>() {
            @Override
            public void onChanged(VolumesResponse volumesResponse) {
                if (volumesResponse != null) {
                    adapter.setResults(volumesResponse.getItems());
                }
            }
        });
        bookSearhViewModel.searchVolumes("guitar");
    }
}