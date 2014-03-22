package com.example.imageapp.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sratanjee on 3/5/14.
 */
public class MainActivity extends FragmentActivity {

    private ArrayList<ModelImage> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_gridview_activity);

        ResponseParser parser = new ResponseParser(this.getApplicationContext());
        images = parser.parseJSONArray(parser.loadJSONFromAsset());

        ListView gv = (ListView) findViewById(R.id.grid_view);
        gv.setAdapter(new GridViewAdapter(this, images));
        gv.setDivider(null);

        // Implement On Item click listener
        gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ImageDetailActivity.class);
                intent.putExtra("MODEL_IMAGE_DETAIL", images.get(position));
                startActivity(intent);
            }
        });
    }
}
