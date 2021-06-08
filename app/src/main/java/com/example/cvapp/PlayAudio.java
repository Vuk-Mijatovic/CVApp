package com.example.cvapp;

import android.content.Context;
import android.media.MediaPlayer;

public class PlayAudio {

    private Context context;
    private MediaPlayer mediaPlayer;

    public PlayAudio(Context context) {
        this.context = context;
    }

     public void playSound(int audioFile) {
        mediaPlayer = MediaPlayer.create(context, audioFile);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
    }


}
