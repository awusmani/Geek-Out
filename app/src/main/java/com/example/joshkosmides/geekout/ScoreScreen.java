package com.example.joshkosmides.geekout;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


/**
 * Created by abdulusmani on 11/21/17.
 */

public class ScoreScreen extends Activity{

    int soundID = R.raw.buttonm;
    Intent intent = null;
    String player = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Connected");
    final DatabaseReference scr = database.getReference("Scores");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // playAudio();
        setContentView(R.layout.scorescreen);


        scr.child("Player 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                TextView pOneScr = (TextView) findViewById(R.id.playeronescore);
                pOneScr.setText(dataSnapshot.getValue().toString());


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        scr.child("Player 2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                TextView pTwoScr = (TextView) findViewById(R.id.playertwoscore);
                pTwoScr.setText(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        intent = getIntent();
        player = intent.getStringExtra("PLAYER");

        Button playagain = findViewById(R.id.playagain);
        final MediaPlayer mpPlayAgin = MediaPlayer.create(this, soundID);

        Button quit = findViewById(R.id.quit);
        final MediaPlayer mpQuit = MediaPlayer.create(this, soundID);


        playagain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopAudio();
                mpPlayAgin.start();
                Intent goScreen;
                if (player.equals("Player 1")) {

                    myRef.child("Player 1").setValue(false);

                    goScreen = new Intent(ScoreScreen.this, HostScreenActivity.class);
                } else {
                    myRef.child("Player 1").setValue(false);
                    goScreen = new Intent(ScoreScreen.this, WaitingActivity.class);
                }


                startActivity(goScreen);


            }
        });

        quit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopAudio();
                mpQuit.start();
                Intent backToHomeIntent = new Intent(ScoreScreen.this, MainActivity.class);
                startActivity(backToHomeIntent);

                myRef.child("Player 1").setValue(false);
                myRef.child("Player 2").setValue(false);

            }
        });


    }

    @Override
    public void onBackPressed() {
        final MediaPlayer mpBack = MediaPlayer.create(this, soundID);
        //stopAudio();
        mpBack.start();
        Toast toast = Toast.makeText(this,"Round has started. There is no going back buddy.", Toast.LENGTH_SHORT);
        toast.show();

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();

        myRef.child(player).setValue(false);
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
