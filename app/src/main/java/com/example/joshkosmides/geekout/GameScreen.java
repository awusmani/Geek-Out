package com.example.joshkosmides.geekout;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import static android.content.ContentValues.TAG;


/**
 * Created by zemichael on 12/2/2017.
 */

public class GameScreen extends Activity {


    int soundID = R.raw.buttonm;
    public String pres = "Presidents", show = "That's 70's Show Characters", drag = "Named Fictional Dragons", quen = "Quentin Taratino Films",
            mike = "Michael Jackson Songs", star = "Star trek Characters", gods = "Greek Gods", marv = "Marvel SuperHeroes", poke = "Famous Pokemon", bllz = "Dragon Ball Z Characters";

    public String[] arrPres = {"George Washington","John Adams","Thomas Jefferson","James Madison","James Monroe","John Quincy Adams","Andrew Jackson","Martin Van Buren","William Harrison","John Tyler", "James Polk","Zachary Taylor","Millard Fillmore","Franklin Pierce","James Buchanan","Abraham Lincoln","Andrew Johnson","Ulysses Grant","Rutherford Hayes","James Garfield","Chester Arthur","Grover Cleveland","Benjamin Harrison","William McKinley","Theodore Roosevelt","William Taft","Woodrow Wilson","Warren Harding","Calvin Coolidge","Herbert Hoover","Franklin Roosevelt","Harry Truman","Dwight Eisenhower","John F. Kennedy","Lyndon Johnson","Richard Nixon","Gerald Ford","Jimmy Carter","Ronald Reagan","George Bush","Bill Clinton","George W. Bush","Barack Obama","Donald Trump"};
    public String[] arrShow = {"Hyde", "Eric Foreman", "Kelso", "Fez", "Kitty", "Donna", "Bob","Jackie", "Laurie", "Randy", "Casey", "Pam", "Leo"};
    public String[] arrDrag = {"Smaug", "Ruth", "Thorn", "Rhaegal", "Saphira", "Draco", "Scorch", "Tharos", "Dragon", "Drogon", "Katla", "Glaedr", "Ruth", "Ramoth", "Rhaegal", "Norberta", "Temeraire", "Saphira", "Viserion", "Falkor", "Pyre", "Pufnstuf"};
    public String[] arrQuen = {"Pulp Fiction", "Django Unchained", "Reservoir Dogs", "Inglorious Basterds", "The Hateful Eight", "Kill Bill"};
    public String[] arrMike = {"Smooth Criminal", "Billie Jean", "Beat It", "Man in the Mirror", "Black or White", "The Way You Make Me Feel", "Remember The Time", "Dirty Diana", "Rock With You", "Thriller", "Heal the World", "Bad", "Wanna be Starting Something", "Don't Stop Till You Get Enough", "You Rock My World", "P.Y.T.", "PYT", "Human Nature", "Who is It", ""};
    public String[] arrStar = {"Spock", "Captain Kirk", "Uhura", "Admiral McCoy", "Scotty", "Captain Sulu", "Chekov", "Hadley", "Kirk", "Sulu", "Brent",};
    public String[] arrGods = {"Zeus", "Hera", "Poseidon", "Demeter", "Ares", "Athena", "Apollo", "Artemis", "Hephaestus", "Aphrodite", "Hermes", "Dionysus", "Hades", "Hypnos", "Nike", "Janus", "Nemesis", "Iris", "Hecate", "Tyche"};
    public String[] arrMarv = {"Spider Man", "Spider-Man", "Captain America", "Iron Man", "Hulk", "Black Widow", "Deadpool", "Wolverine", "Thor", "Daredevil", "Black Panther", "Quicksilver", "Cyclops", "Luke Cage", "Magneto", "Star-Lord", "Groot", "Nick Fury", "Captain Marvel"};
    public String[] arrPoke = {"Pikachu", "Celebi", "Eevee", "Charizard", "Snorlax", "Lugia", "Mewtwo", "Ditto", "Charmander", "Squirtle", "Bulbasaur", "Mew", "Gyarados", "Dragonite", "Magikarp", "Articuno", "Gengar", "Umbreon", "Zapdos", "Raichu", "Blastoise", "Arceus", "Lapras", "Pichu"};
    public String[] arrBllZ = {"Goku", "Vegeta", "Gohan", "Majin Buu", "Frieza", "Trunks", "Bulma", "Piccolo", "Cell", "Krillin", "Goten", "Chi-Chi", "Videl", "Roshi", "Master Roshi", "Nappa", "Raditz", "Tien Shinhan"};

    String currQuestion;

    Object keys[];

    Intent intent = null;
    String player = null;

    public HashMap<String, String[]> qandA = new HashMap<String, String[]>();

    private TextView timerId;
    TextView questionPrompt;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference connected = database.getReference("Connected");
    final DatabaseReference myRef = database.getReference("Scores");
    final DatabaseReference Qidx = database.getReference("Questionidx");

    int n = 2;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamescreen);

        intent = getIntent();
        player = intent.getStringExtra("PLAYER");


        // Read from the database
        Qidx.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                n = dataSnapshot.getValue(Integer.class);
                Log.d("N VALUE","" + n);
                keys =  qandA.keySet().toArray();

                currQuestion = keys[n].toString();

                questionPrompt = (TextView) findViewById(R.id.questionPrompt);
                questionPrompt.setText(currQuestion);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        //playAudio();
        timerId = findViewById(R.id.gameTimer);

            qandA.put(pres,arrPres);
            qandA.put(show,arrShow);
            qandA.put(drag,arrDrag);
            qandA.put(quen,arrQuen);
            qandA.put(mike,arrMike);
            qandA.put(star,arrStar);
            qandA.put(gods,arrGods);
            qandA.put(marv,arrMarv);
            qandA.put(poke,arrPoke);
            qandA.put(bllz,arrBllZ);



        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerId.setText("" + (millisUntilFinished / 1000));

            }
            public void onFinish() {
                //timer finished, go to next activity or finish work here
                Intent scoreScreenIntent = new Intent(GameScreen.this, ScoreScreen.class);
                int score = getResults(n);

                myRef.child(player).setValue(score);

                scoreScreenIntent.putExtra("PLAYER", player.toString());

                startActivity(scoreScreenIntent);
            }

        }.start();
    }

    public int getResults(int n){
        Log.i(TAG, "beg of results");
        EditText entryBox = (EditText) findViewById(R.id.entryBox);
        int score = 0;
        String answers = entryBox.getText().toString();
        List<String> userAnswers = Arrays.asList(answers.split("\\s*\\n"));
        Log.i(TAG, "userans..." + userAnswers);
        List<String> actualAnswers = Arrays.asList(qandA.get(keys[n].toString()));
        Log.i(TAG, "actans..." + actualAnswers);

        for(String userAns : userAnswers){
            Log.i(TAG, "user ans" + userAns);
            for(String actAns: actualAnswers) {
                if(userAns.equalsIgnoreCase(actAns)) {
                    score++;
                }
            }
        }
        return score;
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

        connected.child(player).setValue(false);
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

