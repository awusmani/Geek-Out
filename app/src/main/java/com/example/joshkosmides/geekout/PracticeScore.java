package com.example.joshkosmides.geekout;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by abdulusmani on 11/21/17.
 */

public class PracticeScore extends Activity{

    int soundID = R.raw.buttonm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // playAudio();
        setContentView(R.layout.practicescore);

        Intent intent = getIntent();
        int score = intent.getIntExtra("PracticeSCORE", 0);
        TextView pOneScr = (TextView) findViewById(R.id.playeronescore);
        pOneScr.setText(""+score);



        Button quit = findViewById(R.id.quit);
        final MediaPlayer mpQuit = MediaPlayer.create(this, soundID);




        quit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopAudio();
                mpQuit.start();
                Intent backToHomeIntent = new Intent(PracticeScore.this, MainActivity.class);
                startActivity(backToHomeIntent);

            }
        });


    }

    @Override
    public void onBackPressed() {
        final MediaPlayer mpBack = MediaPlayer.create(this, soundID);
        mpBack.start();
        playAudio();
        Toast toast = Toast.makeText(this,"Hit Play Again for a new game. Hit Quit to go back to the start screen :)", Toast.LENGTH_LONG);
        toast.show();



    }

    public void playAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        startService(objIntent);
    }

    public void stopAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        stopService(objIntent);
    }
}
