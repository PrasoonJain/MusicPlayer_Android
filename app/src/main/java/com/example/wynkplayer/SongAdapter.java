package com.example.wynkplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private ArrayList<Song> songs;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public SongAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }
    public interface OnItemClickListener{
        void onItemClick(TextView txtview,View view, Song song, int position);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public SongAdapter.SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_item,parent,false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull SongAdapter.SongHolder holder, final int position) {
        final Song s=songs.get(position);
        holder.songName.setText(s.getSongName());
        holder.songName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.songName,view, s, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
    public class SongHolder extends RecyclerView.ViewHolder {

        TextView songName;


        public SongHolder(View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.songName);
        }

    }
}
