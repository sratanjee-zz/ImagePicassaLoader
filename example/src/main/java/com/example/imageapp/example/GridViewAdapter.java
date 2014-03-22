package com.example.imageapp.example;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by sratanjee on 3/5/14.
 */
import static android.widget.ImageView.ScaleType.CENTER_CROP;

final class GridViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<String>();
    private ArrayList<ModelImage> images;

    public GridViewAdapter(Context context, ArrayList<ModelImage> images) {
        this.context = context;
        this.images = images;

        //Create String Array based on URLS
        String[] imageUrls = new String[images.size()];

        for (int x = 0; x <images.size(); x++) {
            imageUrls[x] = images.get(x).getUrl();
        }

        // Ensure we get a different ordering of images on each run.
        Collections.addAll(urls, imageUrls);
    }

    public String drawLocation(double latitude, double longitude) {

        Geocoder myLocation = new Geocoder(context, Locale.getDefault());
        List<Address> myList = null;
        String addressStr = "";

        try {
            myList = myLocation.getFromLocation(latitude, longitude, 1);
            if (myList.size()!=0) {
                Address address = (Address) myList.get(0);
                addressStr += address.getAddressLine(0) + ", ";
                addressStr += address.getAddressLine(1) + ", ";
                addressStr += address.getAddressLine(2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressStr;
    }

    @Override public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.userView = (TextView) view.findViewById(R.id.user);
            holder.descriptionView = (TextView) view.findViewById(R.id.description);
            holder.imageView = (RoundedImageView) view.findViewById(R.id.photo);
            holder.dateView = (TextView) view.findViewById(R.id.date);
            holder.locationContainer = (RelativeLayout) view.findViewById(R.id.location_container);
            holder.locationText = (TextView) view.findViewById(R.id.location_description);
            holder.locationView = (ImageView) view.findViewById(R.id.location_marker);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        holder.userView.setText(images.get(position).getUser());
        holder.dateView.setText(images.get(position).getDate());
        holder.descriptionView.setText(images.get(position).getDescription());

        final double latitude = images.get(position).getLatitude();
        final double longitude =  images.get(position).getLongitude();

        String address =
                drawLocation(latitude, longitude);

        if (address.length()==0) {
            address = "Mystery Location";
        }

        holder.locationView.setImageResource(R.drawable.map);
        holder.locationText.setText(address);

        final String finalAddress = address;
        holder.locationContainer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String label = finalAddress;
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";

                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uriAddress = Uri.parse(uriString);

                Intent intent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(uriAddress)));
                context.startActivity(intent);
            }
        });

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fit()
                .into(holder.imageView);

        return view;
    }

    @Override public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        return urls.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView userView;
        TextView dateView;
        TextView descriptionView;
        TextView locationText;
        ImageView locationView;
        RelativeLayout locationContainer;
        RoundedImageView imageView;
    }
}
