package com.example.listnhac;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SongViewModel extends ViewModel {

    // MutableLiveData lưu trữ danh sách bài hát yêu thích
    private final MutableLiveData<List<Song>> favoriteSongs = new MutableLiveData<>(new ArrayList<>());

    // Lấy danh sách bài hát yêu thích dưới dạng LiveData (để tránh sửa đổi trực tiếp)
    public LiveData<List<Song>> getFavoriteSongs() {
        return favoriteSongs;
    }

    // Phương thức thêm bài hát vào danh sách yêu thích
    public boolean addFavoriteSong(Song song) {
        List<Song> currentList = favoriteSongs.getValue();
        if (currentList != null) {
            // Kiểm tra xem bài hát đã tồn tại chưa (dựa vào tên hoặc đường dẫn)
            for (Song s : currentList) {
                if (s.getFilePath().equals(song.getFilePath())) { // Kiểm tra trùng lặp dựa vào đường dẫn
                    return false; // Trả về false nếu bài hát đã có trong danh sách
                }
            }

            // Nếu chưa có, thêm bài hát vào danh sách và cập nhật
            currentList.add(song);
            favoriteSongs.setValue(new ArrayList<>(currentList));  // Tạo bản sao mới để cập nhật
            return true; // Trả về true khi thêm thành công
        }
        return false; // Trả về false nếu không thể thêm (danh sách null hoặc lỗi)
    }

    // Phương thức xóa bài hát khỏi danh sách yêu thích
    public void removeFavoriteSong(Song song) {
        List<Song> currentList = favoriteSongs.getValue();
        if (currentList != null) {
            currentList.remove(song);
            favoriteSongs.setValue(new ArrayList<>(currentList));  // Cập nhật danh sách
        }
    }
}
