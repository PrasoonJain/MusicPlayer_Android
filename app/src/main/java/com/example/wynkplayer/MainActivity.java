package com.example.wynkplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ContentResolver contentResolver;
    Cursor cursor;
    Uri uri;
    Context context;
   SongAdapter songAdapter;
    ArrayList<Song> ListElementsArrayList ;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =  findViewById(R.id.recyclerView);

        context = getApplicationContext();
        ListElementsArrayList = new ArrayList<>();
        songAdapter = new SongAdapter(this,ListElementsArrayList);
        recyclerView.setAdapter(songAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        Toast toast=Toast.makeText(getApplicationContext(),"Click On Any Song to Listen",Toast.LENGTH_SHORT);

        toast.show();




        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final TextView txtview, View view,final  Song song, int position) {
                Log.d("intent","cliked");
                Intent intent=new Intent(context,PlayerActivity.class);
                intent.putExtra("songName",song.getSongName());
                intent.putExtra("songPath",song.getSongUrl());
                startActivity(intent);


            }
        });
        GetAllMediaMp3Files();


    }


    public void GetAllMediaMp3Files(){

        contentResolver = context.getContentResolver();
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        cursor = contentResolver.query(
                uri, // Uri
                null,
                null,
                null,
                null
        );
        if (cursor == null) {
            Toast.makeText(MainActivity.this,"Something Went Wrong.", Toast.LENGTH_LONG);

        } else if (!cursor.moveToFirst()) {
            Log.d("Music List","no music found");
            Toast.makeText(MainActivity.this,"No Music Found on SD Card.", Toast.LENGTH_LONG);

        }
        else {
            int Title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            do {
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                Log.d("song ","name "+name);

                Log.d("got some","name= "+url);
                Song song=new Song(name,url);
                ListElementsArrayList.add(song);

            } while (cursor.moveToNext());
            cursor.close();
            songAdapter = new SongAdapter(MainActivity.this,ListElementsArrayList);



        }
        Log.d("music list","here :  "+ListElementsArrayList);

    }

}
