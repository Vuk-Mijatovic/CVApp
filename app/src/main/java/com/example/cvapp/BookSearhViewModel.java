package com.example.cvapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BookSearhViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    private LiveData<VolumesResponse> volumesResponseLiveData;

    public BookSearhViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        bookRepository = new BookRepository();
        volumesResponseLiveData = bookRepository.getVolumesResponseLiveData();
    }

    public void searchVolumes (String keyword, int startIndex) {
        bookRepository.searchVolumes(keyword, "AIzaSyAQ_cswvQ3PenOYLnuTZ4VORlEp3tfnXtE", startIndex);
    }

    public LiveData<VolumesResponse> getVolumesResponseLiveData() {
        return volumesResponseLiveData;
    }

    public boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
    }
}
