package com.example.infant_cry;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;

import java.io.IOException;

public class PlayThread extends Thread {
    String audioFile;
    MediaPlayer player;
    Context context;

    private SoundPool mSoundPool;
    private int mSoundId;

    public PlayThread(String audioFile, MediaPlayer player,
                      Context context) {
        this.audioFile = audioFile;
        this.player = player;
        this.context = context;
    }


    @Override
    public void run() {
        System.out.println("这里是play thread run function");
        Uri uri = Uri.parse(audioFile);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setVolume(1.0f, 1.0f);
        try {
//            player.setDataSource(context, uri);
            player.setDataSource(audioFile);
        } catch (IOException e) {
            System.out.println("=======play线程exception开始=======");
            throw new RuntimeException(e);
        }
        try {
            player.prepare();
        } catch (IOException e) {
            System.out.println("=====play线程, prepare exception======");
            throw new RuntimeException(e);
        }
        player.start();
        System.out.println("时间长度！！！！！！");
        System.out.println(player.getDuration());
    }
}
