package com.example.imageapp.example;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sratanjee on 3/1/14.
 */
public class ResponseParser {

    private Context context;

    public ResponseParser(Context myContext) {
        this.context = myContext;
    }

    /*This function is simply to load the JSON from the given file, ideally this would
    * would come back from the response and can then parse the JSON directly
    * */
    public JSONArray loadJSONFromAsset() {
        String json = null;
        JSONArray jsonArray;

        try {
            InputStream is = context.getAssets().open("example_response.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            jsonArray = new JSONArray(json);

        } catch (Exception ex) {
            Log.d("EXCEPTION", ex.toString());
            ex.printStackTrace();
            return null;
        }

        return jsonArray;
    }

    public ArrayList<ModelImage> parseJSONArray(JSONArray jsonImageArray) {

        //Parse JSON array and store it into data structure as objects
        ArrayList<ModelImage> imageModelArray= new ArrayList<ModelImage>();
        if (jsonImageArray!=null) {
        for (int x = 0; x < jsonImageArray.length(); x++) {

            ModelImage imageObject = new ModelImage();

            try {
                JSONObject jsonImageObject= (JSONObject) jsonImageArray.get(x);
                imageObject.setDescription(jsonImageObject.optString("description"));
                imageObject.setDate(jsonImageObject.optString("date"));
                imageObject.setUrl(jsonImageObject.optString("url"));
                imageObject.setUser(jsonImageObject.optString("user"));
                imageObject.setLatitude(jsonImageObject.optString("lat"));
                imageObject.setLongitude(jsonImageObject.optString("long"));

                imageModelArray.add(imageObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }

        return imageModelArray;
    }
}
