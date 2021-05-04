package com.example.cvapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    private List<Volume> books;
    Volume currentBook;
    Context context;

    public BookAdapter(List<Volume> books, Context context) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booklisting_recyclerview_item, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        currentBook = books.get(position);

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
        if (currentBook.getVolumeInfo().getReadingModes().isImage()) {
            String imageLink = currentBook.getVolumeInfo().getImageLinks().getThumbnail()
                .replace("http://", "https://");
        Picasso.get().load(imageLink)
                .fit().into(holder.imageView);
        } else {
           holder.imageView.setImageResource(R.drawable.na);
        }

    }

    public void setResults(List<Volume> results) {
        this.books = results;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() { return books.size(); }


    public void clear() {
        int size = books.size();
        if (size > 0) {
            books.clear();
            notifyItemRangeRemoved(0, size);
        }
    }



    class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Uri webPage = Uri.parse(currentBook.getVolumeInfo().getWebPage());
            Intent openPage = new Intent(Intent.ACTION_VIEW, webPage);
            context.startActivity(openPage);
        }
    }
}
