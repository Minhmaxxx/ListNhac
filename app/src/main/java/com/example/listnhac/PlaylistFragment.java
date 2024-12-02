package com.example.listnhac;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {

    private ListView listView;
    private SongAdapter songAdapter;
    private List<Song> songList;
    private SongViewModel songViewModel;

    private static final int REQUEST_PERMISSION_READ_MEDIA_AUDIO = 200;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        listView = view.findViewById(R.id.list_view_songs);

        songViewModel = new ViewModelProvider(requireActivity()).get(SongViewModel.class);
        songList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33): Sử dụng READ_MEDIA_AUDIO
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_MEDIA_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.READ_MEDIA_AUDIO},
                        REQUEST_PERMISSION_READ_MEDIA_AUDIO);
            } else {
                loadMusicFromDevice(getContext());
            }
        } else {
            // Android 12 trở xuống: Sử dụng READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READ_MEDIA_AUDIO);
            } else {
                loadMusicFromDevice(getContext());
            }
        }



        songAdapter = new SongAdapter(getActivity(), songList);

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            Song song = songList.get(position);

            // Thêm bài hát vào mục yêu thích nếu chưa có
            boolean isAdded = songViewModel.addFavoriteSong(song);
            if (isAdded) {
                Toast.makeText(getContext(), song.getName() + " đã được thêm vào nhạc yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu bài hát đã có trong yêu thích
                Toast.makeText(getContext(), song.getName() + " ĐÃ CÓ TRONG NHẠC YÊU THÍCH!!", Toast.LENGTH_SHORT).show();
            }

            return true;
        });
        listView.setAdapter(songAdapter);

        return view;
    }

    private void loadMusicFromDevice(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };

        // Thêm bộ lọc cho các file có đuôi .mp3, .mp4, .m4a
//        String selection = MediaStore.Audio.Media.DATA + " LIKE ?";
//        String[] selectionArgs = new String[]{
//                "%/.mp3",  // Lọc file .mp3
//                "%/.mp4",  // Lọc file .mp4
//                "%/.m4a"   // Lọc file .m4a
//        };

        Cursor cursor = contentResolver.query(musicUri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String duration = formatDuration(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                songList.add(new Song(R.drawable.ic_launcher_background, name, artist, duration, filePath));
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Toast.makeText(context, "Không tìm thấy nhạc trong thiết bị", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatDuration(long durationInMs) {
        int minutes = (int) (durationInMs / 1000 / 60);
        int seconds = (int) (durationInMs / 1000 % 60);
        return String.format("%d:%02d", minutes, seconds);
    }
}
