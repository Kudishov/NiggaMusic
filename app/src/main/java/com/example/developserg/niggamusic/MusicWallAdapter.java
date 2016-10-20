package com.example.developserg.niggamusic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by DevelopSerg on 20.10.2016.
 */
public class MusicWallAdapter extends BaseAdapter {
    private ArrayList<String> users, messages;
    private Context context;
    private VKList<VKApiAudio> audioList;
    private VKList wallList;

    public MusicWallAdapter(Context context, VKList wallList) {
        this.context = context;
        this.wallList = wallList;
    }

    @Override
    public int getCount() {
        return wallList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void ExtractAudio (){

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.audio_item, null);
        setData.artist = (TextView) view.findViewById(R.id.audio_artist);
        setData.title = (TextView) view.findViewById(R.id.audio_title);
        setData.artist.setText(audioList.get(position).artist);
        setData.title.setText(audioList.get(position).title);

        return view;
    }

    public class SetData {
        TextView artist, title;
    }
}