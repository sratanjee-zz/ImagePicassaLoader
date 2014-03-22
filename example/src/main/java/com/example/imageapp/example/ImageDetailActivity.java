package com.example.imageapp.example;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by sratanjee on 3/6/14.
 */
public class ImageDetailActivity extends Activity {

    private TextView userView;
    private TextView dateView;
    private TextView descriptionView;
    private TextView locationText;
    private ImageView locationView;
    private RelativeLayout locationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_view);

        Typeface font = Typeface.createFromAsset(this.getAssets(),"Roboto-Regular.ttf");

        userView = (TextView) findViewById(R.id.user);
        userView.setTypeface(font);
        dateView = (TextView) findViewById(R.id.date);
        dateView.setTypeface(font);
        descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setTypeface(font);
        locationText = (TextView) findViewById(R.id.location_description);
        locationText.setTypeface(font);
        locationView = (ImageView) findViewById(R.id.location_marker);
        locationView.setImageResource(R.drawable.map);

        locationContainer = (RelativeLayout) findViewById(R.id.location_container);

        RoundedImageView imageView = (RoundedImageView) findViewById(R.id.photo);

        Intent intent = getIntent();
        final ModelImage imageObject = (ModelImage) intent.getSerializableExtra("MODEL_IMAGE_DETAIL");

        userView.setText(imageObject.getUser());
        dateView.setText((imageObject.getDate()));
        descriptionView.setText(imageObject.getDescription());

        final double latitude = imageObject.getLatitude();
        final double longitude =  imageObject.getLongitude();

        final String address =
                drawLocation(imageObject.getLatitude(), imageObject.getLongitude());
        locationText.setText(address);

        locationContainer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*String uri = String.format(Locale.ENGLISH, "geo:%f,%f",
                        imageObject.getLatitude(), imageObject.getLongitude());*/

                String label = address;
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";

                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uriAddress = Uri.parse(uriString);

                Intent intent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(uriAddress)));
                startActivity(intent);
            }
        });


        Picasso.with(this)
                .load(imageObject.getUrl())
                .fit()
                .into(imageView);
    }

    public String drawLocation(double latitude, double longitude) {

        Geocoder myLocation = new Geocoder(this, Locale.getDefault());
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
            else {
                locationView.setVisibility(locationView.GONE);
                locationText.setVisibility(locationText.GONE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressStr;
    }
}