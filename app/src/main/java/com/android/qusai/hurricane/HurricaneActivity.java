package com.android.qusai.hurricane;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HurricaneActivity extends Activity {
    private List<Track> mListItems;
    private SCTrackAdapter mAdapter;
    private static final String TAG = "HurricaneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hurricane);

        mListItems = new ArrayList<Track>();
        ListView listView = (ListView)findViewById(R.id.track_list_view);
        mAdapter = new SCTrackAdapter(this,mListItems);
        listView.setAdapter(mAdapter);

//        Log.e("stuff","Stuff");
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Config.API_URL).build();
        SCService scService = restAdapter.create(SCService.class);
        scService.getRecentTracks(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
//
////                if (tracks == null) {
////                    Log.e(TAG, "First track title: " + tracks.get(0).getTitle());
//                }
                loadTracks(tracks);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error: " + error);
            }
        });
    }

    private  void loadTracks(List<Track> tracks){
        mListItems.clear();
        mListItems.addAll(tracks);
        mAdapter.notifyDataSetChanged();

    }
}