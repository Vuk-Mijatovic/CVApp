package com.example.cvapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessOnScrollListener extends RecyclerView.OnScrollListener {

    int previousTotalItemCount;
    boolean loading = true;
    int visibleThreshold = 10;
    LinearLayoutManager layoutManager;

    public EndlessOnScrollListener(LinearLayoutManager layoutManager) {
        super();
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }
        if (!loading && lastVisibleItemPosition + visibleThreshold == totalItemCount) {
            onLoadMore();
            loading = true;
        }

    }

    protected abstract void onLoadMore();
}
