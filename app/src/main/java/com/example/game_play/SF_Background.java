package com.example.game_play;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class SF_Background {
    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private ByteBuffer indexBuffer;

    private int[] textures = new int[1];//pocet nahravanych obrazkov
    private float vertices[] = {//pokryva celu obrazovku x y a z suradnice
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
    };
    private float texture[] = {//rohy obrazku spaja s rohom stvorca
            0.0f, 0.0f,
            1.0f, 0f,
            1, 1.0f,
            0f, 1f,
    };
    private byte indices[] = {//stena stvorca je rozdelena na dva trojuholniky proti smeru hod ruciciek
            0, 1, 2,
            0, 2, 3,
    };

    public SF_Background() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        textureBuffer = byteBuffer.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);

        indexBuffer = ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    public void loadTexture(GL10 gl, int texture, Context context) {
        InputStream imageStream = context.getResources().openRawResource(texture);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(imageStream);
        } catch (Exception e) {
        } finally {
            //Always clear and close
            try {
                imageStream.close();
                imageStream = null;
            } catch (IOException e) {
            }
        }
        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);//namapovanie textur vrchol
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);//pohyb drahy
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);//pohyb drahy

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }
    public void draw(GL10 gl){//sa vola pokazdom ked bude nutne nakreslit pozadie na rozdiel od metody loadtexture ktora sa vola pri inicializacii hry
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textures[0]);
        //redukovanie  a igonorovala akekoli vrcholy, ktore nie su natoicene k smerom hracovi
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexBuffer);
        gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,textureBuffer);

        //vykresluje sa textura na vrcholi a vsetko ostatne sa ddeaktivusju
        gl.glDrawElements(GL10.GL_TRIANGLES,indices.length,GL10.GL_UNSIGNED_BYTE,indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

    }
}
