package com.example.game_play;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class SF_Game extends Activity {
    private SF_GameView sf_gameView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sf_gameView = new SF_GameView(this);
         setContentView(sf_gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sf_gameView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sf_gameView.onPause();
    }
}
