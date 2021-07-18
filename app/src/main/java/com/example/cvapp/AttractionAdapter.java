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
    private ItemClickListener clickListener;

    public void addAttraction(String title, String shortDescription, String address, int imageResourceId) {
        attractions.add(new Attraction(title, shortDescription, address, imageResourceId));
    }

    public AttractionAdapter(ItemClickListener clickListener) {
        attractions = new ArrayList<>();
        this.clickListener = clickListener;
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
        holder.imageView.setImageResource(attractions.get(position).getImageResourceId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(attractions.get(position));
            }
        });

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
            this.imageView = itemView.findViewById(R.id.itemImageView);
        }
    }

    public interface ItemClickListener {
        public void onItemClick(Attraction attraction);
    }

}
