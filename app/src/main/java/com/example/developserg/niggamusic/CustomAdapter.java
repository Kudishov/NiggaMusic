package com.example.developserg.niggamusic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by DevelopSerg on 20.10.2016.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<String> users, messages;
    private Context context;
    private VKList<VKApiAudio> audioList;
    ArrayList<String> artistList = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<String> artist, ArrayList<String> title) {
        this.context = context;
        this.artistList = artist;
        this.titleList = title;
    }

    @Override
    public int getCount() {
        return artistList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.audio_item, null);
        setData.artist = (TextView) view.findViewById(R.id.audio_artist);
        setData.title = (TextView) view.findViewById(R.id.audio_title);
        setData.artist.setText(artistList.get(position));
        setData.title.setText(titleList.get(position));

        return view;
    }

    public class SetData {
        TextView artist, title;
    }
}
