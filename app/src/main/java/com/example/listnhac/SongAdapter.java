package com.example.listnhac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listnhac.Song;

import java.util.List;

public class SongAdapter extends BaseAdapter {

    private Context context;
    private List<Song> songList;

    public SongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout item_song.xml
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listnhac, parent, false);
        }

        // Bind dữ liệu vào các view
        Song song = songList.get(position);

        ImageView songImageView = convertView.findViewById(R.id.song_image);
        TextView songNameTextView = convertView.findViewById(R.id.song_name);
        TextView artistNameTextView = convertView.findViewById(R.id.artist_name);
        TextView durationTextView = convertView.findViewById(R.id.song_duration);

        songImageView.setImageResource(R.drawable.music_icon);
        songNameTextView.setText(song.getName());
        artistNameTextView.setText(song.getArtist());
        durationTextView.setText(song.getDuration());

        return convertView;
    }
}
