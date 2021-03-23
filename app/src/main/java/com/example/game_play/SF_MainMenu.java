package com.example.game_play;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.game_play.object.SFengine;

public class SF_MainMenu extends Activity {
    com.google.android.material.button.MaterialButton start_button;
    com.google.android.material.button.MaterialButton stop_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SFengine sFengine = new SFengine(this);

        start_button = findViewById(R.id.id_play_button);
        stop_button = findViewById(R.id.id_stop_button);

        start_button.setHapticFeedbackEnabled(SFengine.HAPTIC_BUTTON_FEEDBACK);
        stop_button.setHapticFeedbackEnabled(SFengine.HAPTIC_BUTTON_FEEDBACK);


        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_button.animate().rotation(1800).setDuration(3000);
                Intent game = new Intent(getApplicationContext(),SF_Game.class);
                SF_MainMenu.this.startActivity(game);
            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean clean = false;
                clean = sFengine.onExit(v);
                if(clean){
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }
            }
        });
    }
}
