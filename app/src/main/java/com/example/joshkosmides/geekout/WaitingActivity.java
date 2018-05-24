package com.example.joshkosmides.geekout;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by joshkosmides on 12/2/17.
 */

public class WaitingActivity extends Activity {

    int soundID = R.raw.buttonm;
    String player = "Player 2";


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Connected");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //playAudio();
        setContentView(R.layout.waiting_activity);

        myRef.child(player).setValue(true);

        // Read from the database
        myRef.child("Player 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                boolean connected = dataSnapshot.getValue(Boolean.class);

                Log.d("AFAGGAG","" + connected);
                if(connected){
                    Intent gotoGame = new Intent (WaitingActivity.this, GameScreen.class);
                    gotoGame.putExtra("PLAYER", player.toString());
                    startActivity(gotoGame);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        myRef.child(player).setValue(true);
    }

    public void playAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        startService(objIntent);
    }

    public void stopAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        stopService(objIntent);

    }

    @Override
    public void onBackPressed() {
        final MediaPlayer mpBack = MediaPlayer.create(this, soundID);
        //stopAudio();
        mpBack.start();
        myRef.child(player).setValue(false);
        Intent backHomeIntent = new Intent (WaitingActivity.this, MainActivity.class);
        startActivity(backHomeIntent);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        myRef.child(player).setValue(false);
    }

}
