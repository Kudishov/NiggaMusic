package com.example.developserg.niggamusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button buttonGo;
    private String[] scope = new String[]{VKScope.AUDIO};
    VKList<VKApiAudio> audioVKList;
    VKApiWall wallsList;
    VKList wallsVKList;
    CustomAdapter customAdapter;
    MusicWallAdapter musicWallAdapter;
    Button wall;
    ArrayList<String> artistList = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VKSdk.initialize(this);
        VKSdk.login(this, scope);

        listView = (ListView) findViewById(R.id.listView);
        buttonGo = (Button) findViewById(R.id.button_go);
        wall = (Button) findViewById(R.id.button_wall);

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKRequest vkRequestAudio = VKApi.audio().get(VKParameters.from(VKApiConst.USER_ID, 1798006));
                vkRequestAudio.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        customAdapter.notifyDataSetChanged();
//                        //получение аудиозаписи
//                        audioVKList = (VKList<VKApiAudio>) response.parsedModel;
//                        customAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKRequest vkRequestWall = VKApi.wall().get(VKParameters.from(VKApiConst.GROUP_ID, 82675968));
                vkRequestWall.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest vkRequestWall = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, "-82675968", VKApiConst.COUNT, 3000));
                vkRequestWall.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        try {
                            JSONObject jsonObject = (JSONObject) response.json.get("response");
                            JSONArray jsonItems = (JSONArray) jsonObject.get("items");
                            for (int i = 0; i < jsonItems.length(); i++) {
                                JSONObject items = jsonItems.getJSONObject(i);
                                JSONArray jsonAttachments = (JSONArray) items.get("attachments");
                                for (int j = 0; j < jsonAttachments.length(); j++) {
                                    JSONObject type = jsonAttachments.getJSONObject(j);
                                    String types = type.getString("type");
                                    if (types.equals("audio")) {
                                        JSONObject audio = type.getJSONObject("audio");
                                        String artist = audio.getString("artist");
                                        artistList.add(artist);
                                        String title = audio.getString("title");
                                        titleList.add(title);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        musicWallAdapter = new MusicWallAdapter(MainActivity.this, wallsVKList);
//                        listView.setAdapter(musicWallAdapter);
                    }
                });

                Toast.makeText(getApplicationContext(), "Good", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "Bad", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        customAdapter = new CustomAdapter(getApplicationContext(), artistList, titleList);
        listView.setAdapter(customAdapter);
    }
}
