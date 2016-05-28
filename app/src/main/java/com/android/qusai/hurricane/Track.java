package com.android.qusai.hurricane;

/**
 * Created by qusai on 5/28/16.
 */
import com.google.gson.annotations.SerializedName;
public class Track {
    // serializedName used to convert the json to java object or vais versa

    @SerializedName("Title")
    private String mTitle;

    @SerializedName("id")
    private int mID;

    @SerializedName("stream_url")
    private String mStream_URL;

    @SerializedName("artwork_url")
    private String mArtworkURL;

    public String getmTitle(){
        return mTitle;
    }

    public  int getmID(){
        return mID;
    }

    public String getmStream_URL(){
        return mStream_URL;
    }

    public String getmArtworkURL(){
        return mArtworkURL;
    }
}
