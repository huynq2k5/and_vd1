package com.example.vd1.ui.library;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vd1.R;
import com.example.vd1.data.model.Song;
import com.example.vd1.ui.adapter.SongAdapter;
import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private RecyclerView rvSongs;
    private SongAdapter adapter;
    private List<Song> songList;

    // Biến theo dõi bài hiện tại
    private int currentSongPosition = -1;

    public LibraryFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSongs = view.findViewById(R.id.rvSongs);
        rvSongs.setLayoutManager(new LinearLayoutManager(getContext()));
        songList = new ArrayList<>();

        // THÊM DỮ LIỆU CỦA BẠN VÀO ĐÂY
        songList.add(new Song("In the morning light", R.raw.morning));
        songList.add(new Song("Love in blue", R.raw.blue));
        songList.add(new Song("Toccata", R.raw.toccata));
        songList.add(new Song("Full moon magic", R.raw.moon));

        // Gắn Adapter với sự kiện click mới
        adapter = new SongAdapter(songList, this::handleSongClick);
        rvSongs.setAdapter(adapter);
    }

    private void handleSongClick(Song song, int position) {
        // TRƯỜNG HỢP 1: Click vào bài ĐANG phát
        if (position == currentSongPosition && mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                // Đang hát -> Tạm dừng
                mediaPlayer.pause();
                adapter.setPlayingState(position, false); // Đổi icon thành Play
                Toast.makeText(getContext(), "Tạm dừng", Toast.LENGTH_SHORT).show();
            } else {
                // Đang tạm dừng -> Hát tiếp
                mediaPlayer.start();
                adapter.setPlayingState(position, true); // Đổi icon thành Pause
                Toast.makeText(getContext(), "Tiếp tục phát", Toast.LENGTH_SHORT).show();
            }
        }
        // TRƯỜNG HỢP 2: Click vào bài MỚI (hoặc lần đầu tiên)
        else {
            playNewSong(song, position);
        }
    }

    private void playNewSong(Song song, int position) {
        releaseMediaPlayer();
        currentSongPosition = position; // Cập nhật vị trí bài mới

        try {
            mediaPlayer = MediaPlayer.create(getContext(), song.getResourceId());
            if (mediaPlayer != null) {
                mediaPlayer.start();

                // Cập nhật giao diện: Bài ở vị trí này đang "true" (đang hát) -> Hiện icon Pause
                adapter.setPlayingState(position, true);
                Toast.makeText(getContext(), "Đang phát: " + song.getTitle(), Toast.LENGTH_SHORT).show();

                // Khi hát xong thì tự động reset icon về Play
                mediaPlayer.setOnCompletionListener(mp -> {
                    adapter.setPlayingState(position, false);
                    mediaPlayer.seekTo(0); // Tua về đầu (tùy chọn)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseMediaPlayer();
    }
}