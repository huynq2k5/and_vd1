package com.example.vd1.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vd1.R;
import com.example.vd1.data.model.Song;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;
    private OnItemClickListener listener;

    // Biến lưu vị trí bài đang phát (-1 là chưa phát bài nào)
    private int playingPosition = -1;
    private boolean isPlaying = false; // Trạng thái đang chạy hay tạm dừng

    public interface OnItemClickListener {
        void onItemClick(Song song, int position);
    }

    public SongAdapter(List<Song> songList, OnItemClickListener listener) {
        this.songList = songList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.tvTitle.setText(song.getTitle());

        // LOGIC ĐỔI ICON:
        if (position == playingPosition && isPlaying) {
            // Nếu là bài đang chọn VÀ đang phát -> Hiện icon Pause
            holder.imgState.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            // Các trường hợp khác -> Hiện icon Play
            holder.imgState.setImageResource(android.R.drawable.ic_media_play);
        }

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(song, position);
        });
    }

    // Hàm này được gọi từ Fragment để cập nhật giao diện
    public void setPlayingState(int position, boolean isPlaying) {
        int oldPosition = this.playingPosition;
        this.playingPosition = position;
        this.isPlaying = isPlaying;

        // Cập nhật lại giao diện cho bài cũ (để đổi về icon Play)
        if (oldPosition != -1) {
            notifyItemChanged(oldPosition);
        }
        // Cập nhật giao diện cho bài mới (để đổi icon Play/Pause)
        if (position != -1) {
            notifyItemChanged(position);
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imgState; // Khai báo ImageView

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvSongTitle);
            imgState = itemView.findViewById(R.id.imgState); // Ánh xạ
        }
    }
}