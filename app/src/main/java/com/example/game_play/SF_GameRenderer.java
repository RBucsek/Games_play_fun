package com.example.game_play;

import android.opengl.GLSurfaceView;

import com.example.game_play.object.SFengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SF_GameRenderer implements GLSurfaceView.Renderer {
    private SF_Background sf_background = new SF_Background();
    private SF_Background sf_background2 = new SF_Background();
    private SF_GoodGuy player1 = new SF_GoodGuy();

    private int goodGuyBankFrames = 0;
    private long loopStart = 0;
    private long loopEnd = 0;
    private long loopRuntime =0;

    private float bgScroll1;
    private float bgScroll2;

    /*tato metoda scrollbakground1 nam posuva pozadie hry
    reset modolovej matici
    nahra texturovu maticu a posunie o osy y o hodnotu premennej bgScroll1
    vykresli pozadie a vyjme maticu zo zasobniku


     */
    private void scrollBackground2(GL10 gl) {//posuvanie modelu
        if (bgScroll2 == Float.MAX_VALUE) {
            bgScroll2 = 0f;
        }
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.3f, 1f, 1f);//yy je zvecsenie obrazaka
        gl.glTranslatef(1.5f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.01f, bgScroll2, 0.01f);//posuvanie textury
        sf_background2.draw(gl);
        gl.glPopMatrix();

        bgScroll2 += SFengine.SCROLL_BACKGROUND_2;
        gl.glLoadIdentity();
    }

    private void scrollBackground1(GL10 gl) {
        if (bgScroll1 == Float.MAX_VALUE) {
            bgScroll1 = 0f;
        }
        //posuvanie modelu na zaklade matici OpenGL
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, bgScroll1, 0.0f);//posuvanie textury

        //nakresleni pozadia pomocou metody draw
        sf_background.draw(gl);
        gl.glPopMatrix();
        bgScroll1 += SFengine.SCROLL_BACKGROUND_1;
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {//vytvorenie zobrazenia glsurfaceview,textur
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        //pridat nahravanie textur pre obrazok pozadia
        sf_background.loadTexture(gl, SFengine.BACKGROUND_LAYER_ONE, SFengine.context);
        sf_background2.loadTexture(gl, SFengine.BACKGROUND_LAYER_TWO, SFengine.context);
        player1.loadTexture(gl, SFengine.PLAYER_SHIP, SFengine.context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {//zmena velkosti okna
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {//vykreslovacia jednotka kresli snimek na obrazovku
        loopStart = System.currentTimeMillis();
        try {
            if(loopRuntime < SFengine.GAME_THREAD_FPS_SLEEP) {
                Thread.sleep(SFengine.GAME_THREAD_FPS_SLEEP - loopRuntime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        scrollBackground1(gl);
        scrollBackground2(gl);

        movePlayer1(gl); // vykreslovanie hry volanie);

        //tuto volame veskere dalsi vykreslovanie
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        loopEnd = System.currentTimeMillis();
        loopRuntime = (loopEnd - loopStart);
    }

    private void movePlayer1(GL10 gl) {//skuma vstupni objekt a na zaklade hodnoty tohoto vstupu prevedie urcity kod
        switch (SFengine.playerFlightAction) {
            case SFengine.PLAYER_BANK_LEFT_1://nastavenie skalovatelnosti na 0.25 na osach x a y a pohyb do lava
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f, .25f, 1f);
                //posuvanie vrcholov pomocou glTranslatf s premennou playerBankposX
                if (goodGuyBankFrames < SFengine.PLAYER_FRAMES_BETWEEN_ANI && SFengine.playerBankPosX > 0) {//snimkovanie kedy sa ma prejst na dalsi obrazok
                    SFengine.playerBankPosX -= SFengine.PLAYER_BANK_SPEED;
                    gl.glTranslatef(SFengine.playerBankPosX, 0f, 0f);//pohyb postavy na osy x

                    //osa x archu spritu prebieha od 0 do 1. Podelime 4mi kedze 4 obrazky mame tak kazdy sprite bude obsadzovat 0.25 osy x, prvy obrazok od 0 dp 0.24 druhy od 0.25 do 0,49 atd

                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.75f, 0.0f, 0.0f);
                    // sledovanie ked mame prejst na druhy obrazok

                    goodGuyBankFrames += 1;
                }
                else if(goodGuyBankFrames >= SFengine.PLAYER_FRAMES_BETWEEN_ANI && SFengine.playerBankPosX > 0){
                    SFengine.playerBankPosX -= SFengine.PLAYER_BANK_SPEED;
                    gl.glTranslatef(SFengine.playerBankPosX,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.0f,0.25f,0.0f);
                }
                else {
                    gl.glTranslatef(SFengine.playerBankPosX, 0f, 0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.0f, 0.0f, 0.0f);
                    //nastavenie pohybu lietadla aby nepreslo za okraj pomocou if else
                }
                //nakreslujeme metodu draw a vyjmeme maticu zo zasobnika.
                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                break;
                //prikaz case a pohyb dolava a impolentovanie dvoch snimkov spritove animacie je hotovy
            case SFengine.PLAYER_BANK_RIGHT_1:
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f,.25f,1f);
                if(goodGuyBankFrames < SFengine.PLAYER_FRAMES_BETWEEN_ANI && SFengine.playerBankPosX < 3){
                    SFengine.playerBankPosX += SFengine.PLAYER_BANK_SPEED;
                    gl.glTranslatef(SFengine.playerBankPosX,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.25f,0.0f,0.0f);
                    goodGuyBankFrames +=1; // posun do prava
                } else if(goodGuyBankFrames >= SFengine.PLAYER_FRAMES_BETWEEN_ANI && SFengine.playerBankPosX < 3){
                    SFengine.playerBankPosX += SFengine.PLAYER_BANK_SPEED;
                    gl.glTranslatef(SFengine.playerBankPosX,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.50f,0.0f,0.0f);
                }else{
                    gl.glTranslatef(SFengine.playerBankPosX,0f,0f);
                    gl.glMatrixMode(GL10.GL_TEXTURE);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.0f,0.0f,0.0f);
                }
                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                break;
            case SFengine.PLAYER_RELEASE://hrac pri pohybe postavou uvolni ovladanie vtedy sa zavola RELEASE, je to uplne rovnake ako default cast
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f, .25f, 1f);//skalovatelnost, znizujeme lod o 0,25 %
                gl.glTranslatef(SFengine.playerBankPosX, 0f, 0f); //os x playerbanPosx, aktualna pozicia hraca kde to bolo naposledy zaznamenane
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, 0.0f, 0.0f);
                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                goodGuyBankFrames += 1;
                break;
            default: // neprevedie sa ziadna akcia
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f, .25f, 1f);//skalovatelnost, znizujeme lod o 0,25 %
                gl.glTranslatef(SFengine.playerBankPosX, 0f, 0f); //os x playerbanPosx, aktualna pozicia hraca kde to bolo naposledy zaznamenane
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, 0.0f, 0.0f);
                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                break;
        }
    }
}
