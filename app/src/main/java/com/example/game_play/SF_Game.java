package com.example.game_play;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.game_play.object.SFengine;

public class SF_Game extends Activity implements SensorEventListener {
    private SF_GameView sf_gameView;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sf_gameView = new SF_GameView(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        setContentView(sf_gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sf_gameView.onResume();
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {//poslucha udalosti ktory bude nacuvat ake kolik udalosti co spravime
        float x = event.getX();
        float y = event.getY();
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            int height = SFengine.display.getHeight() / 4;
            int playableAre = SFengine.display.getHeight() - height; // cast obrazovky kde sa ma na dotyk reagovat

            if (y > playableAre) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //prst uvolnenie displeja
                        if (x < SFengine.display.getWidth() / 2) {
                            SFengine.playerFlightAction = SFengine.PLAYER_BANK_LEFT_1;
                        } else {
                            SFengine.playerFlightAction = SFengine.PLAYER_BANK_RIGHT_1;
                        }
                        break;
                    case MotionEvent.ACTION_UP: // stlacenie displeja
                        SFengine.playerFlightAction = SFengine.PLAYER_RELEASE;
                        break;
                }
            }
        } else {
            Point size = new Point();
            SFengine.display.getSize(size);
            int height = size.y / 4;
            int playableAre = size.y - height;
            if (y > playableAre) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //prst uvolnenie displeja
                        if (x < size.x / 2) {
                            SFengine.playerFlightAction = SFengine.PLAYER_BANK_LEFT_1;
                        } else {
                            SFengine.playerFlightAction = SFengine.PLAYER_BANK_RIGHT_1;
                        }
                        break;
                    case MotionEvent.ACTION_UP: // stlacenie displeja
                        SFengine.playerFlightAction = SFengine.PLAYER_RELEASE;
                        break;
                }
            }
        }
        return false;
    }
*/
    @Override
    protected void onPause() {
        super.onPause();
        sf_gameView.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];

            Point size = new Point();
            SFengine.display.getSize(size);
            int height = size.y / 4;
            int playableAre = size.y - height;
            if (y > playableAre) {

                if (x < size.x / 2) {
                    SFengine.playerFlightAction = SFengine.PLAYER_BANK_LEFT_1;
                } else {
                    SFengine.playerFlightAction = SFengine.PLAYER_BANK_RIGHT_1;
                }
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
