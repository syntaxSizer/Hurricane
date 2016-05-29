package com.android.qusai.hurricane;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HurricaneActivity extends Activity {
    private static final String TAG = "HurricaneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hurricane);

//        Log.e("stuff","Stuff");
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Config.API_URL).build();
        SCService scService = restAdapter.create(SCService.class);
        scService.getRecentTracks(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
//                Log.e("stuff2",tracks.toString());
                if (tracks == null) {
                    Log.e(TAG, "First track title: " + tracks.get(0).getTitle());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error: " + error);
            }
        });
    }
}