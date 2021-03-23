package com.example.game_play;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SF_GameView extends GLSurfaceView {
    private SF_GameRenderer sf_gameRenderer;

    public SF_GameView(Context context) {
        super(context);
        sf_gameRenderer = new SF_GameRenderer();
        this.setRenderer(sf_gameRenderer);
    }
}

