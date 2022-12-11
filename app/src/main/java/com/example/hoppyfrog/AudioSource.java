package com.example.hoppyfrog;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AudioSource extends Component
{
    private List<Integer> soundPool_ids = new ArrayList<>();
    private List<Integer> mediaPlayer_ids = new ArrayList<>();
    private int soundStream;

    AudioSource(GameObject p_gameObject)
    {
        id = "AUDIO_SOURCE";
        gameObject = p_gameObject;
    }

    void LoadSoundPoolFile(int id)
    {
        int sid = GameView.soundManager.LoadSound(id);

        Log.e("SOUND", "LoadSoundPoolFile: " + sid );

        soundPool_ids.add(sid);
    }

    void PauseMediaPlayer(int index)
    {
        GameView.soundManager.PauseMediaPlayer(index);
    }

    void LoadMediaPlayerFile(int id)
    {
        mediaPlayer_ids.add(mediaPlayer_ids.size());
        GameView.soundManager.CreateMediaPlayer(id);
    }

    void PlaySound(int index)
    {
        soundStream = GameView.soundManager.playSound(soundPool_ids.get(index));
    }

    void StopSound()
    {
        GameView.soundManager.stopSound(soundStream);
    }

    void PlayMediaPlayer(int index)
    {
        GameView.soundManager.PlayMediaPlayer(index);
    }

    void PlayMediaPlayers()
    {
        Log.e("SOUND", "PlayMediaPlayers: PLAY" );
        GameView.soundManager.PlayMediaPlayers();
    }
}
