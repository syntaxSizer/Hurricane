package com.android.qusai.hurricane;

/**
 * Created by qusai on 5/28/16.
 */
import com.google.gson.annotations.SerializedName;
public class Track {
    // serializedName used to convert the json to java object or vais versa

    @SerializedName("title")
    private String mTitle;

    @SerializedName("id")
    private int mID;

    @SerializedName("stream_url")
    private String mStream_URL;

    @SerializedName("artwork_url")
    private String mArtworkURL;

    public String getTitle(){
        return mTitle;
    }

    public  int getID(){
        return mID;
    }

    public String getStream_URL(){
        return mStream_URL;
    }

    public String getArtworkURL(){
        return mArtworkURL;
    }
}
