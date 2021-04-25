package com.example.cvapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private ArrayList<Book> books;

    public BookAdapter(ArrayList<Book> books) {
        this.books = books;
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
        Book currentBook = books.get(position);
        holder.authorView.setText(currentBook.getAuthor());
        holder.titleView.setText(currentBook.getTitle());
        holder.descriptionView.setText(currentBook.getDescription());
        Picasso.get().load(currentBook.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

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
