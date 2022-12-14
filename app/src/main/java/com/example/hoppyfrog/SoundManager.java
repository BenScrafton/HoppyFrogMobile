package com.example.hoppyfrog;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class SoundManager
{
    private MediaPlayer soundtrack_mediaPlayer;
    private MediaPlayer ambient_mediaPlayer;

    private List<MediaPlayer> mediaPlayers = new ArrayList<>();
    private List<Integer> mediaDuration = new ArrayList<>();
    private SoundPool soundPool;

    private Context context;

    SoundManager(Context p_context)
    {
        context = p_context;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .build();
        }
        else
        {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
    }

    int LoadSound(int id)
    {
        int sound_id = soundPool.load(context, id, 1);
        return sound_id;
    }

    public void CreateMediaPlayer(int soundId)
    {
        MediaPlayer m = MediaPlayer.create(context, soundId);
        mediaPlayers.add(m);
    }

    public void PlayMediaPlayer(int index)
    {
        if (!mediaPlayers.get(index).isPlaying())
        {
            mediaPlayers.get(index).start();
            mediaPlayers.get(index).setLooping(true);
        }
    }

    public void PlayMediaPlayers()
    {
        for(MediaPlayer m : mediaPlayers)
        {
            if (!m.isPlaying())
            {
                m.seekTo(0);
                m.start();
            }
        }
    }

    public void PauseMediaPlayers()
    {
        int i = 0;

        mediaDuration.clear();
        for(MediaPlayer m : mediaPlayers)
        {
            if (m.isPlaying())
            {
                m.pause();

                Integer duration = m.getCurrentPosition();
                mediaDuration.add(duration);

                i++;
            }
        }
    }

    public void PauseMediaPlayer(int index)
    {
        MediaPlayer mp =  mediaPlayers.get(index);

        if (mp.isPlaying()){
            mp.pause();
        }
    }

    public void StopMediaPlayers()
    {
        for(MediaPlayer m : mediaPlayers)
        {
            m.release();
            m = null;
        }
    }

    public void resumeMediaPlayers()
    {
        int i = 0;

        if(mediaDuration.size() > 0)
        {
            for(MediaPlayer m : mediaPlayers)
            {
                m.seekTo(mediaDuration.get(i));
                m.start();
                i++;
            }
        }
    }

    public void stopSound(int id)
    {
        soundPool.stop(id);
    }

    public void resumeSounds()
    {
        soundPool.autoResume();
    }

    public void pauseSounds()
    {
        soundPool.autoPause();
    }

    public void destroySoundPool()
    {
        soundPool.release();
        soundPool = null;
    }

    public int playSound(int id)
    {
        int streamID = soundPool.play(id, 0.8f, 0.8f, 1 ,0,1f);
        return streamID;
    }
}
