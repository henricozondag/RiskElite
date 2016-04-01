package mamahetogames.riskelite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.lang.reflect.Array;
import java.util.Objects;

public class MapScreen extends Activity implements View.OnTouchListener {

    mapView v;
    Bitmap armyIcon;
    Bitmap landKaart;
    float x, y, floatX,floatY;
    int intY, intX, nieuwX, nieuwY;
    int screenWidth, screenHeight;
    boolean provincieGeklikt;
    Float nieuwFloatX = new Float(nieuwX);
    Float nieuwFloatY = new Float(nieuwY);

    Paint black, white;

    Rect ProvinciesLijst[] = new Rect[12];

    Rect Zeeland =         new Rect(8,   1139,    239,    1464);
    Rect Friesland =       new Rect(617,  154,    790,    436);
    Rect Groningen =       new Rect(825,  107,    1065,   289);
    Rect Drenthe =         new Rect(813,  313,    992,    576);
    Rect Flevoland =       new Rect(553,  523,    723,    764);
    Rect Overijssel =      new Rect(784,  614,    1018,   825);
    Rect NoordHolland =    new Rect(289,  409,    506,    811);
    Rect Utrecht =         new Rect(430,  857,    594,    998);
    Rect Gelderland =      new Rect(623,  846,    926,    1089);
    Rect Limburg =         new Rect(652,  1262,   810,    1698);
    Rect NoordBrabant =    new Rect(289,  1139,   651,    1408);
    Rect ZuidHolland =     new Rect(169,  866,    409,    1115);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new mapView(this);
        v.setOnTouchListener(this);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        Log.i("x size" + size.x ,"y size" + size.y);

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setTextSize(50);

        white = new Paint();
        white.setColor(Color.WHITE);

        ProvinciesLijst[0] = Zeeland;
        ProvinciesLijst[1] = Friesland;
        ProvinciesLijst[2] = Groningen;
        ProvinciesLijst[3] = Drenthe;
        ProvinciesLijst[4] = Flevoland;
        ProvinciesLijst[5] = Overijssel;
        ProvinciesLijst[6] = NoordHolland;
        ProvinciesLijst[7] = Utrecht;
        ProvinciesLijst[8] = Gelderland;
        ProvinciesLijst[9] = Limburg;
        ProvinciesLijst[10] = NoordBrabant;
        ProvinciesLijst[11] = ZuidHolland;

        armyIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.toy_soldier_clip_art_small);
        landKaart = BitmapFactory.decodeResource(getResources(), R.mipmap.provincieskaart);
        armyIcon = Bitmap.createScaledBitmap(armyIcon, screenWidth / 15 , screenHeight / 15, false);
        landKaart = Bitmap.createScaledBitmap(landKaart, screenWidth, screenHeight,false);
        setContentView(v);


    }

    @Override
    public boolean onTouch(View v, MotionEvent me) {
        // me = motion event dus heeft de waarde waar je het scherm aanraakt in zich
        // met het aanraken verander je dus het x en y waarde, de runnable update dan meteen het
        // scherm, dan verandert dus de positie van de bal.

        int intX = (int) me.getX();
        int intY = (int) me.getY();

        switch (me.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("klik locatie", "" + me.getX() + "  " + me.getY());
                Integer legerCoordinaten[] = zetLeger(intX, intY);
                nieuwX =  legerCoordinaten[0];
                nieuwY =  legerCoordinaten[1];
                floatX = (float) nieuwX;
                floatY = (float) nieuwY;
                Log.i("nieuwX "+ nieuwX,"nieuw Y" + nieuwY);
                break;

            //case MotionEvent.ACTION_UP:
            //    break;
        }
        // return true zodat je meer dan een keer kan klikken met effect
        return true;
    }

    class mapView extends SurfaceView implements Runnable {

        Thread t = null;
        SurfaceHolder holder;
        boolean isItOK = false; // true mag je tekenen, anders niet

        public mapView (Context context) {
            super(context);
            holder = getHolder();

        }

        @Override
        public void run() {
            while (isItOK == true) {
                // tekenen op canvas!
                if (!holder.getSurface().isValid()) {
                    continue; // continue zorgt ervoor dat er opnieuw gecheckt wordt of er iets geldig is
                }
                Canvas c = holder.lockCanvas();
                c.drawBitmap(landKaart, 0, 0, null);
                if (floatX != 0) {
                    c.drawBitmap(armyIcon, floatX - (armyIcon.getWidth() / 2), floatY - (armyIcon.getHeight() / 2), null);
                    c.drawCircle(floatX - 25 ,floatY - 80, 30, black);
                    c.drawCircle(floatX - 25 ,floatY - 80, 28, white);
                    c.drawText("1", floatX - (armyIcon.getWidth() / 2), floatY - (armyIcon.getHeight() / 2), black);
                    c.drawText("1", floatX,  floatY, white);
                }

                holder.unlockCanvasAndPost(c);


            }
        }

        public void pause() {
            isItOK = false;
            while (true) {
                try{
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            }
            t = null;
        }

        public void resume() {
            isItOK = true;
            t = new Thread(this);
            t.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    public Integer[] zetLeger (int x, int y) {

        Integer legerCoordinaten[] = new Integer[2];

        for (int i = 0; i < ProvinciesLijst.length; i++ ) {
            if (ProvinciesLijst[i].contains(x,y)){
                legerCoordinaten[0] = ProvinciesLijst[i].centerX();
                legerCoordinaten[1] = ProvinciesLijst[i].centerY();
            }
        }
        if (legerCoordinaten[0] == null) {
            Log.i("niets"," lege coordinaten");
            legerCoordinaten[0] = 0;
            legerCoordinaten[1] = 0;
        }
        return legerCoordinaten;
    }

    }
