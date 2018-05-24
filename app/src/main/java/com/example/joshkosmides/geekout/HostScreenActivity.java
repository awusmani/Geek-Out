package com.example.joshkosmides.geekout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;


/**
 * Created by joshkosmides on 11/21/17.
 */


public class HostScreenActivity extends Activity {

    int soundID = R.raw.buttonm;

    static ArrayList<Integer> hosts = new ArrayList<Integer>();
    private TextView hostTextView;
    private TextView waiting1;
    private TextView waiting2;
    private TextView waiting3;
    private TextView waiting4;
    private Button startButton;
    private int numGuests = 0;
    private final IntentFilter intentFilter = new IntentFilter();

    String player = "Player 1";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Connected");
    final DatabaseReference Qidx = database.getReference("Questionidx");

    Random rand = new Random();
    int n;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //playAudio();
        setContentView(R.layout.host_screen_activity);


        n = rand.nextInt(10) + 0;


        waiting1 = findViewById(R.id.waiting1);
        startButton = findViewById(R.id.startButton);
        final MediaPlayer mpStart = MediaPlayer.create(this, soundID);



        Qidx.setValue(n);

        // Read from the database
        myRef.child("Player 2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                boolean connected = dataSnapshot.getValue(Boolean.class);

                if(connected){
                    waiting1.setText("Player 2 connected");
                }
                else{
                    waiting1.setText("Waiting for Opponent...");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                myRef.child("Player 2").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        if(dataSnapshot.getValue(boolean.class)){

                            mpStart.start();

                            myRef.child(player).setValue(true);
                            Intent gotoGame = new Intent (HostScreenActivity.this, GameScreen.class);
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
        });

    }

    @Override
    public void onBackPressed() {
        final MediaPlayer mpBack = MediaPlayer.create(this, soundID);
        //stopAudio();
        mpBack.start();
        Intent backHomeIntent = new Intent (HostScreenActivity.this, MainActivity.class);
        startActivity(backHomeIntent);

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
    protected void onDestroy(){
        super.onDestroy();

        myRef.child(player).setValue(false);
    }

}
