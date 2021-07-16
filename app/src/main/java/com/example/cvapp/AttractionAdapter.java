package com.example.cvapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder> {
    private List<Attraction> attractions;

    public void addAttraction(String title, String shortDescription, String address) {
        attractions.add(new Attraction(title, shortDescription, address));
    }

    public AttractionAdapter() {
        attractions = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attractions_list_item_view, parent, false);
        return new AttractionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AttractionViewHolder holder, int position) {
        holder.titleView.setText(attractions.get(position).getTitle());
        holder.shortDescriptionView.setText(attractions.get(position).getShortDescription());
        holder.addressView.setText(attractions.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    class AttractionViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView shortDescriptionView;
        private final TextView addressView;
        private final ImageView imageView;


        public AttractionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.itemTitleView);
            this.shortDescriptionView = itemView.findViewById(R.id.itemDescriptionView);
            this.addressView = itemView.findViewById(R.id.itemAddressView);
            this.imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
