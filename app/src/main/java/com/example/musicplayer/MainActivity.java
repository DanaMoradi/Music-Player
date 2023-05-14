package com.example.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.adapter.MusicAdapter;
import com.example.musicplayer.databinding.ActivityMainBinding;
import com.example.musicplayer.model.Music;
import com.google.android.material.slider.Slider;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements onMusicClickListener {

    private List<Music> musicList = Music.getList();
    private ActivityMainBinding binding;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private MusicStat musicStat = MusicStat.STOPPED;
    private boolean isDrag;
    MusicAdapter musicAdapter;

    private static int cursor;


    enum MusicStat {
        PLAYING, STOPPED, PAUSED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        RecyclerView recyclerView = binding.rvMainPlayList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        musicAdapter = new MusicAdapter(musicList, this);
        recyclerView.setAdapter(musicAdapter);


        onMusicChange(musicList.get(cursor));
        binding.ivMainPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (musicStat) {
                    case PLAYING:
                        musicStat = MusicStat.PAUSED;
                        binding.ivMainPlay.setImageResource(R.drawable.ic_play_32dp);
                        mediaPlayer.pause();
                        break;
                    case PAUSED:
                    case STOPPED:
                        musicStat = MusicStat.PLAYING;
                        binding.ivMainPlay.setImageResource(R.drawable.ic_pause_24dp);
                        mediaPlayer.start();
                        break;
                }
            }
        });

        binding.sliderMain.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                binding.tvMainStartTime.setText(Music.convertMillisToString((long) value));
            }
        });

        binding.sliderMain.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                isDrag = true;
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                isDrag = false;
                mediaPlayer.seekTo((int) slider.getValue());
            }
        });

        binding.ivMainForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });

        binding.ivMainBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPrev();
            }
        });


    }


    private void onMusicChange(Music music) {
        musicAdapter.notifyMusicChange(music);
        binding.sliderMain.setValue(0);
        mediaPlayer = MediaPlayer.create(this, music.getMusicFileResId());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isDrag) {
                                    binding.sliderMain.setValue(mediaPlayer.getCurrentPosition());
                                }
                            }
                        });
                    }
                }, 1000, 1000);
                binding.tvMainEndTime.setText(Music.convertMillisToString(mediaPlayer.getDuration()));
                binding.sliderMain.setValueTo(mediaPlayer.getDuration());
                musicStat = MusicStat.PLAYING;
                binding.ivMainPlay.setImageResource(R.drawable.ic_pause_24dp);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        goNext();
                    }
                });

            }
        });

        binding.ivMainArtist.setImageResource(music.getArtistResId());
        binding.ivMainCover.setImageResource(music.getCoverResId());
        binding.tvMainSongName.setText(music.getName());
        binding.tvMainArtistName.setText(music.getArtist());

    }

    private void goNext() {
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
        if (cursor < musicList.size() - 1) {
            cursor += 1;
        } else {
            cursor = 0;
        }

        onMusicChange(musicList.get(cursor));
    }

    private void goPrev() {
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
        if (cursor == 0) {
            cursor = musicList.size() - 1;
        } else cursor -= 1;

        onMusicChange(musicList.get(cursor));
    }

    @Override
    public void onItemClick(Music music, int position) {
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
        cursor = position;
        onMusicChange(musicList.get(cursor));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Error", "onError");
        timer.cancel();
        mediaPlayer.release();
        mediaPlayer = null;
    }


}