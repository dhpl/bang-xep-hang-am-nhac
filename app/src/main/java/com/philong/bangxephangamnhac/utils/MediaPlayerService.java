package com.philong.bangxephangamnhac.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by Long on 6/15/2017.
 */

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener, AudioManager.OnAudioFocusChangeListener{


    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if(requestAudioFocus() == false){
                stopSelf();
            }
            if(mMediaPlayer == null){
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(intent.getStringExtra("url"));
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(this);
                mMediaPlayer.prepareAsync();
            }else{
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(intent.getStringExtra("url"));
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(this);
                mMediaPlayer.prepareAsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
        removeAudioFocus();
        stopSelf();

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch(focusChange){
            case AudioManager.AUDIOFOCUS_GAIN:
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }
                mMediaPlayer.release();
                mMediaPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.setVolume(0.1f, 0.1f);
                }
                break;
        }
    }

    private boolean requestAudioFocus(){
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            return true;
        }
        return false;
    }

    private boolean removeAudioFocus(){
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager.abandonAudioFocus(this);
    }
}
