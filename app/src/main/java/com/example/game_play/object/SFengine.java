package com.example.game_play.object;

import android.content.Context;
import android.view.Display;
import android.view.View;

import com.example.game_play.R;

public class SFengine {
    public static final int GAME_THREAD_DELAY = 4000;
    public static final int MENU_BUTTON_ALPHA = 0;
    public static final boolean HAPTIC_BUTTON_FEEDBACK = true;
    public static final int BACKGROUND_LAYER_ONE = R.drawable.backgroundstars;
    public static Context context;
    public static float SCROLL_BACKGROUND_1 = .002f;
    public static final int GAME_THREAD_FPS_SLEEP = (1000 / 60);
    public static float SCROLL_BACKGROUND_2 = .007f;//rychlejsie to pojde ako to prve baground1
    public static final int BACKGROUND_LAYER_TWO = R.drawable.debris;

    public static int playerFlightAction = 0;
    public static final int PLAYER_SHIP = R.drawable.good_sprite;
    public static final int PLAYER_BANK_LEFT_1 = 1;
    public static final int PLAYER_RELEASE = 3;
    public static final int PLAYER_BANK_RIGHT_1 = 4;
    public static final int PLAYER_FRAMES_BETWEEN_ANI = 9;
    public static final float PLAYER_BANK_SPEED = .1f;
    public static final float playerBankPosX = 1.75f;
    public static Display display;

    public SFengine(Context context) {
        this.context = context;
    }


    public boolean onExit(View v) {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
