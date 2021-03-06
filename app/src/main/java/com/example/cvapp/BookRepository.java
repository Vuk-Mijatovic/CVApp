package com.example.cvapp;

import android.accounts.NetworkErrorException;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.nio.channels.NoConnectionPendingException;

import okhttp3.OkHttpClient;
import okhttp3.internal.connection.ConnectInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {

    private static final String BASE_URL = "https://www.googleapis.com/";

    private BookSearchService bookSearchService;
    private MutableLiveData<VolumesResponse> volumesResponseLiveData;
    private MutableLiveData<Boolean> failureResponseLiveData;

    public BookRepository() {
        volumesResponseLiveData = new MutableLiveData<>();
        failureResponseLiveData = new MutableLiveData<>();
        OkHttpClient client = new OkHttpClient.Builder().build();
        bookSearchService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(BookSearchService.class);
    }

    public void searchVolumes(String keyword, String apiKey, int startIndex) {
        bookSearchService.searchVolumes(keyword, apiKey, startIndex, 20)
                .enqueue(new Callback<VolumesResponse>() {
                    @Override
                    public void onResponse(Call<VolumesResponse> call, Response<VolumesResponse> response) {
                        if (response.body() != null) {
                            volumesResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<VolumesResponse> call, Throwable t) {
                        failureResponseLiveData.postValue(true);
                        Log.i("Response is", "unsuccessful");
                    }
                });
    }

    public MutableLiveData<VolumesResponse> getVolumesResponseLiveData() {
        return volumesResponseLiveData;
    }

    public MutableLiveData<Boolean> getFailureResponseLiveData() {
        return failureResponseLiveData;
    }
}
