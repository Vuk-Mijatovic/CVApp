package com.example.cvapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private List<Volume> books = new ArrayList<>();


    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booklisting_recyclerview_item, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Volume currentBook = books.get(position);

        holder.titleView.setText(currentBook.getVolumeInfo().getTitle());

        List<String> authorsList = currentBook.getVolumeInfo().getAuthors();

        if (authorsList != null) {
            String authors = authorsList.get(0);
            for (int i = 1; i < authorsList.size(); i++ ) {
                authors = authors + ", " + authorsList.get(i);
            }
             holder.authorView.setText(authors);
        }

        holder.descriptionView.setText(currentBook.getVolumeInfo().getDescription());
        String imageLink = currentBook.getVolumeInfo().getImageLinks().getThumbnail()
                .replace("http://", "https://");
        Picasso.get().load(imageLink)
                .fit().into(holder.imageView);
    }

    public void setResults(List<Volume> results) {
        this.books = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return books.size(); }

    class BookHolder extends RecyclerView.ViewHolder {
        private final TextView authorView;
        private final TextView titleView;
        private final ImageView imageView;
        private final TextView descriptionView;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            this.authorView = itemView.findViewById(R.id.author_view);
            this.titleView = itemView.findViewById(R.id.title_view);
            this.descriptionView = itemView.findViewById(R.id.description_view);
            this.imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
