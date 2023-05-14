package com.example.musicplayer.model;

import com.example.musicplayer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Music {

    private int id;
    private String name;
    private String artist;
    private int coverResId;
    private int artistResId;
    private int musicFileResId;

    public int getMusicFileResId() {
        return musicFileResId;
    }

    public void setMusicFileResId(int musicFileResId) {
        this.musicFileResId = musicFileResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getCoverResId() {
        return coverResId;
    }

    public void setCoverResId(int coverResId) {
        this.coverResId = coverResId;
    }

    public int getArtistResId() {
        return artistResId;
    }

    public void setArtistResId(int artistResId) {
        this.artistResId = artistResId;
    }


    public static List<Music> getList() {
        List<Music> musicList = new ArrayList<>();
        Music music = new Music();
        music.setArtist("Evan Band");
        music.setName("Chehel Gis");
        music.setArtistResId(R.drawable.music_1_artist);
        music.setCoverResId(R.drawable.music_1_cover);
        music.setMusicFileResId(R.raw.muisc_1);

        Music music1 = new Music();
        music1.setArtist("Reza Sadeghi");
        music1.setName("Tanha tarin");
        music1.setCoverResId(R.drawable.music_2_cover);
        music1.setArtistResId(R.drawable.music_2_artist);
        music1.setMusicFileResId(R.raw.music_2);

        Music music2 = new Music();
        music2.setArtist("Reza Bahram");
        music2.setName("Hich");
        music2.setCoverResId(R.drawable.music_3_cover);
        music2.setArtistResId(R.drawable.music_3_artist);
        music2.setMusicFileResId(R.raw.music_3);

        musicList.add(music);
        musicList.add(music1);
        musicList.add(music2);
        return musicList;
    }

    public static String convertMillisToString(long durationInMillis) {

        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        return String.format(Locale.US, "%02d:%02d", minute, second);
    }


}
