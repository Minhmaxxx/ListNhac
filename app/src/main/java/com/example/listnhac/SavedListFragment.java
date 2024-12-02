package com.example.listnhac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class SavedListFragment extends Fragment {

    private ListView listsavedview;
    private SongAdapter songSavedAdapter;
    private List<Song> songsavedList;
    private SongViewModel songViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_list, container, false);

        listsavedview = view.findViewById(R.id.list_saved_songs);
        songsavedList = new ArrayList<>();
        songSavedAdapter = new SongAdapter(getActivity(), songsavedList);
        listsavedview.setAdapter(songSavedAdapter);

        // Lấy ViewModel và cập nhật danh sách yêu thích
        songViewModel = new ViewModelProvider(requireActivity()).get(SongViewModel.class);
        songViewModel.getFavoriteSongs().observe(getViewLifecycleOwner(), favoriteSongs -> {
            songsavedList.clear();
            songsavedList.addAll(favoriteSongs);
            songSavedAdapter.notifyDataSetChanged();
        });

        // Xử lý sự kiện long-click để xóa bài hát khỏi danh sách yêu thích
        listsavedview.setOnItemLongClickListener((parent, view1, position, id) -> {
            Song song = songsavedList.get(position);

            // Xóa bài hát khỏi danh sách yêu thích
            songViewModel.removeFavoriteSong(song);
            Toast.makeText(getContext(), song.getName() + " đã bị xóa khỏi nhạc yêu thích", Toast.LENGTH_SHORT).show();

            return true; // Trả về true để báo rằng sự kiện đã được xử lý
        });

        return view;
    }
}
