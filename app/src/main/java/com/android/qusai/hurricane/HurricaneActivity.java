package com.android.qusai.hurricane;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.IOException;
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
    private TextView mSelectedTrackTitle;
    private ImageView mSelectedTrackImage;
    private MediaPlayer mMediaPlayer;
    private ImageView mPlayerControl;
    private static final String TAG = "HurricaneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hurricane);

        // music play
        mMediaPlayer = new MediaPlayer();
        // setting audio stream type
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){


            @Override
            public void onPrepared(MediaPlayer mp) {
                togglePlayPause();
            }

            // checking the music player state
            private void togglePlayPause(){

                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                    mPlayerControl.setImageResource(R.drawable.ic_play);



                }
                else {
                    mMediaPlayer.start();
                    mPlayerControl.setImageResource(R.drawable.ic_pause);
                }
            }
        });



        mListItems = new ArrayList<Track>();
        ListView listView = (ListView)findViewById(R.id.track_list_view);
        mAdapter = new SCTrackAdapter(this,mListItems);
        listView.setAdapter(mAdapter);


        //adding the selected track to the play bar at the bottom
        mSelectedTrackTitle = (TextView) findViewById(R.id.selected_track_title);
        mSelectedTrackImage = (ImageView) findViewById(R.id.selected_track_image);
        mPlayerControl = (ImageView) findViewById(R.id.player_control);

        // adding a clicklistener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Track track = mListItems.get(position);


                mSelectedTrackTitle.setText(track.getTitle());
                Picasso.with(HurricaneActivity.this).load(track.getArtworkURL()).into(mSelectedTrackImage);

                //checking if the musicplayer is playing
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                    mMediaPlayer.reset();

                }

                try{
                    mMediaPlayer.setDataSource(track.getStream_URL() + "?client_id=" + Config.CLIENT_ID);
                    mMediaPlayer.prepareAsync();


                }catch (IOException e){
                    e.printStackTrace();

                }

            }
        });

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