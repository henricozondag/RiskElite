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
    float floatX,floatY;
    int nieuwX, nieuwY, screenWidth, screenHeight, aantalLegers;

    Paint black, white;

    Rect[] ProvinciesLijst = new Rect[12];
    Bitmap[] BezettingLegers = new Bitmap[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new mapView(this);
        v.setOnTouchListener(this);

        aantalLegers = 3;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        Log.i("" + ScaleY(1139),"" + ScaleX(8) );
        Log.i("x size" + size.x ,"y size" + size.y);

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setTextSize(50);

        white = new Paint();
        white.setColor(Color.WHITE);

        Rect Zeeland =      new Rect(ScaleX(8),     ScaleY(1139),   ScaleX(239),    ScaleY(1464));
        Rect Friesland =    new Rect(ScaleX(617),   ScaleY(154),    ScaleX(790),    ScaleY(436));
        Rect Groningen =    new Rect(ScaleX(825),   ScaleY(107),    ScaleX(1065),   ScaleY(289));
        Rect Drenthe =      new Rect(ScaleX(813),   ScaleY(313),    ScaleX(992),    ScaleY(576));
        Rect Flevoland =    new Rect(ScaleX(553),   ScaleY(523),    ScaleX(723),    ScaleY(764));
        Rect Overijssel =   new Rect(ScaleX(784),   ScaleY(614),    ScaleX(1018),   ScaleY(825));
        Rect NoordHolland = new Rect(ScaleX(289),   ScaleY(409),    ScaleX(506),    ScaleY(811));
        Rect Utrecht =      new Rect(ScaleX(430),   ScaleY(857),    ScaleX(594),    ScaleY(998));
        Rect Gelderland =   new Rect(ScaleX(623),   ScaleY(846),    ScaleX(926),    ScaleY(1089));
        Rect Limburg =      new Rect(ScaleX(652),   ScaleY(1262),   ScaleX(810),    ScaleY(1698));
        Rect NoordBrabant = new Rect(ScaleX(289),   ScaleY(1139),   ScaleX(651),    ScaleY(1408));
        Rect ZuidHolland =  new Rect(ScaleX(169),   ScaleY(866),    ScaleX(409),    ScaleY(1115));

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

        //Bitmap BezetZeeland = new Bitmap();


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
                c.drawRect(ScaleX(5), ScaleY(5), ScaleX(430), ScaleY(100), black);
                c.drawRect(ScaleX(15), ScaleY(15), ScaleX(420), ScaleY(90), white);
                c.drawText("Legers zetten: ", ScaleX(25), ScaleY(70), black);
                c.drawText( "" + aantalLegers , ScaleX(350) , ScaleY(70), black);

                // Als er in een provincievlak gedrukt is, zet daar legertje neer!
                if (floatX != 0) {

                    c.drawBitmap(armyIcon, floatX - (armyIcon.getWidth() / 2), floatY - (armyIcon.getHeight() / 2), null);
                    c.drawCircle(floatX - ScaleY(25), floatY - ScaleY(80), ScaleY(30), black);
                    c.drawCircle(floatX - ScaleX(25), floatY - ScaleX(80), ScaleX(28), white);
                    c.drawText("1", floatX - (armyIcon.getWidth() / 2), floatY - (armyIcon.getHeight() / 2), black);
                    c.drawText("1", floatX, floatY, white);
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
                aantalLegers--;
            }
        }
        if (legerCoordinaten[0] == null) {
            Log.i("niets"," lege coordinaten");
            legerCoordinaten[0] = 0;
            legerCoordinaten[1] = 0;
        }
        return legerCoordinaten;
    }

    public Integer ScaleX (int integer) {
        int result;
        result = (int) Math.round((double) integer/1080 * screenWidth);
        return result;
    }

    public Integer ScaleY (int integer) {
        int result;
        result = (int) Math.round((double) integer/1776 * screenHeight);
        return result;
    }

    }