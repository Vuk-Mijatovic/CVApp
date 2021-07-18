package com.example.cvapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static final String TITLE = "title";
    private static final String SHORT_DESC = "shortDesc";
    private static final String ADDRESS = "address";
    private static final String IMAGE_ID = "imageId";

    private String title;
    private String address;
    private int imageId;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String title, String shortDesc, String address, int imageId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(SHORT_DESC, shortDesc);
        args.putString(ADDRESS, address);
        args.putInt(IMAGE_ID, imageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            address = getArguments().getString(ADDRESS);
            imageId = getArguments().getInt(IMAGE_ID);
        } else {
            title = getString(R.string.knez_mihailova_name);
            address = getString(R.string.knez_mihailova_contact);
            imageId = R.drawable.knez_mihailova;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView titleView = view.findViewById(R.id.title_view);
        titleView.setText(title);
        TextView addressView = view.findViewById(R.id.address_view);
        addressView.setText(address);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageId);

        return view;
    }
}

//todo add lorem ipsem to constructor? and add more items to list, why scroll in not working in tablet
