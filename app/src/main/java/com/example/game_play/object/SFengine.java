package com.example.game_play.object;

import android.content.Context;
import android.view.View;

import com.example.game_play.R;

public class SFengine {
    public static final int GAME_THREAD_DELAY = 4000;
    public static final int MENU_BUTTON_ALPHA = 0;
    public static final boolean HAPTIC_BUTTON_FEEDBACK = true;
    public static final int BACKGROUND_LAYER_ONE = R.drawable.backgroundstars;
    public static Context context;
    public static float SCROLL_BACKGROUND_1 = .002f;
    public static final int GAME_THREAD_FPS_SLEEP =(1000/60);
    public static float SCROLL_BACKGROUND_2 = .007f;//rychlejsie to pojde ako to prve baground1
    public static final int BACKGROUND_LAYER_TWO= R.drawable.debris;

    public SFengine(Context context){
        this.context=context;
    }


    public boolean onExit(View v) {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
