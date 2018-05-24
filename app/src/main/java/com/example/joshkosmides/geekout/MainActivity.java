package com.example.joshkosmides.geekout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int soundID = R.raw.buttonm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playAudio();

        final Button hostButton = findViewById(R.id.hostButton);
        final MediaPlayer mpHost = MediaPlayer.create(this, soundID);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopAudio();
                mpHost.start();
                Intent hostScreenIntent = new Intent (MainActivity.this, HostScreenActivity.class);
                startActivity(hostScreenIntent);
            }
        });

        final Button joinButton = findViewById(R.id.joinButton);
        final MediaPlayer mpJoin = MediaPlayer.create(this, soundID);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopAudio();
                mpJoin.start();
                Intent joinScreenIntent = new Intent (MainActivity.this, WaitingActivity.class);
                startActivity(joinScreenIntent);
            }
        });

        final Button rulesButton = findViewById(R.id.rulesButton);
        final MediaPlayer mpRules = MediaPlayer.create(this, soundID);

        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopAudio();
                mpRules.start();
                Intent rulesScreenIntent = new Intent (MainActivity.this, RulesScreenActivity.class);
                startActivity(rulesScreenIntent);
            }
        });

        final Button practiceButton = findViewById(R.id.practiceButton);
        final MediaPlayer mpPractice = MediaPlayer.create(this, soundID);

        practiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopAudio();
                mpPractice.start();
                Intent practiceScreenIntent = new Intent (MainActivity.this, PracticeScreen.class);
                startActivity(practiceScreenIntent);
            }
        });

    }

//    public void playAudio(View view) {
//        Intent objIntent = new Intent(this, PlayAudio.class);
//        startService(objIntent);
//    }

    public void playAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        startService(objIntent);
    }

    public void stopAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        stopService(objIntent);
    }
//    public void stopAudio(View view) {
//        Intent objIntent = new Intent(this, PlayAudio.class);
//        stopService(objIntent);
//    }
}
