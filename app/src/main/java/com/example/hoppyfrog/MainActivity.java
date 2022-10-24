package com.example.hoppyfrog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button playBtn;
    Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.play_btn)
                {
                    OpenGame();
                }
                else if (view.getId() == R.id.settings_btn)
                {
                    OpenSettings();
                } ;
            }
        };

        playBtn = (Button) findViewById(R.id.play_btn);
        playBtn.setOnClickListener(listener);
        settingsBtn = (Button) findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(listener);
    }

    public void OpenGame()
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void OpenSettings()
    {

    }
}