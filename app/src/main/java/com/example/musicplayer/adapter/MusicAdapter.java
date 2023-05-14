package com.example.musicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.musicplayer.R;
import com.example.musicplayer.model.Music;
import com.example.musicplayer.onMusicClickListener;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private List<Music> musicList;
    private int playMusicPos = -1;
    private onMusicClickListener listener;

    public MusicAdapter(List<Music> musicList, onMusicClickListener listener) {
        this.musicList = musicList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.onBind(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView artistName;
        TextView songName;
        LottieAnimationView animation;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_music);
            artistName = itemView.findViewById(R.id.tv_music_artist);
            songName = itemView.findViewById(R.id.tv_music_name);
            animation = itemView.findViewById(R.id.la_music_effect);
        }

        private void onBind(Music item) {
            img.setImageResource(item.getCoverResId());
            artistName.setText(item.getArtist());
            songName.setText(item.getName());

            if (getAdapterPosition() == playMusicPos) {
                animation.setVisibility(View.VISIBLE);
            } else {
                animation.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item, getAdapterPosition());
                }
            });
        }


    }


    public void notifyMusicChange(Music music) {
        int index = musicList.indexOf(music);
        if (index != -1) {
            if (index != playMusicPos) {
                notifyItemChanged(playMusicPos);
                playMusicPos = index;
                notifyItemChanged(playMusicPos);
            }

        }
    }


}
