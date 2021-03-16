package com.example.game_play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.game_play.object.SFengine;

public class StarFighter_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splaschscreen);

        new Handler().postDelayed(new Runnable() {
            public void run() {
               Intent intent = new Intent(getApplicationContext(),SF_MainMenu.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        }, SFengine.GAME_THREAD_DELAY);
    }
}