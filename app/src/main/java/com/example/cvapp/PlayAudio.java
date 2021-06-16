package com.example.cvapp;

import android.content.Context;
import android.media.MediaPlayer;

public class PlayAudio {

    private Context context;
    private MediaPlayer mediaPlayer;
    private boolean playerIsReleased = true;

    public PlayAudio(Context context) {
        this.context = context;
    }

    public void playSound(int audioFile) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, audioFile);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playerIsReleased = false;
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                playerIsReleased = true;
            }
        });
    }

    public void stopPlayer() {

        if (!playerIsReleased && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            playerIsReleased = true;
            mediaPlayer = null;
        }
    }

    public boolean playerIsNull() {
        return mediaPlayer == null;
    }


}
