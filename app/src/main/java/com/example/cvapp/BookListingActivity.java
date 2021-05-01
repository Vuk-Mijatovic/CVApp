package com.example.cvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cvapp.MainActivity;
import com.example.cvapp.R;

import static java.security.AccessController.getContext;

public class BookListingActivity extends MainActivity {

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

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchTextView = findViewById(R.id.text_input);
                String keyWord = searchTextView.getText().toString().trim();

                if (keyWord == null || keyWord.isEmpty()) {
                    Toast.makeText(BookListingActivity.this, "Please enter a search term.", Toast.LENGTH_SHORT).show();
                } else {
                    bookSearhViewModel.searchVolumes(keyWord);
                }
            }
        });
    }
}