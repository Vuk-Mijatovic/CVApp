package com.example.cvapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookSearchService {

    @GET("/books/v1/volumes")
    Call<VolumesResponse> searchVolumes(
            @Query("q") String query,
            @Query("key") String apiKey,
            @Query("startIndex") int startIndex,
            @Query("maxResults") int maxResults
    );
}
