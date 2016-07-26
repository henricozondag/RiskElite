package mamahetogames.riskelite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;


public class MapScreen extends Activity implements View.OnTouchListener {

    mapView v;
    Bitmap landKaart, armyRed, armyBlue, armyYellow, armyGreen, countryArmy;
    float coordX, coordY;
    int screenWidth, screenHeight, aantalLegers, active_game_id, active_player_id;
    boolean attackTurnBoolean;

    Paint black, white;
    MyDBHandler db = new MyDBHandler(this);

    Rect[] ProvinciesLijst = new Rect[12];
    String[] ProvincieStringNaam = new String[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        attackTurnBoolean = false;
        super.onCreate(savedInstanceState);
        v = new mapView(this);
        v.setOnTouchListener(this);

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

        ProvinciesLijst[0] = Zeeland;               ProvincieStringNaam[0] = "Zeeland";
        ProvinciesLijst[1] = Friesland;             ProvincieStringNaam[1] = "Friesland";
        ProvinciesLijst[2] = Groningen;             ProvincieStringNaam[2] = "Groningen";
        ProvinciesLijst[3] = Drenthe;               ProvincieStringNaam[3] = "Drenthe";
        ProvinciesLijst[4] = Flevoland;             ProvincieStringNaam[4] = "Flevoland";
        ProvinciesLijst[5] = Overijssel;            ProvincieStringNaam[5] = "Overijssel";
        ProvinciesLijst[6] = NoordHolland;          ProvincieStringNaam[6] = "NoordHolland";
        ProvinciesLijst[7] = Utrecht;               ProvincieStringNaam[7] = "Utrecht";
        ProvinciesLijst[8] = Gelderland;            ProvincieStringNaam[8] = "Gelderland";
        ProvinciesLijst[9] = Limburg;               ProvincieStringNaam[9] = "Limburg";
        ProvinciesLijst[10] = NoordBrabant;         ProvincieStringNaam[10] = "NoordBrabant";
        ProvinciesLijst[11] = ZuidHolland;          ProvincieStringNaam[11] = "ZuidHolland";

        armyRed = BitmapFactory.decodeResource(getResources(),R.mipmap.red_soldier);        armyRed = Bitmap.createScaledBitmap(armyRed, screenWidth / 15 , screenHeight / 15, false);
        armyBlue = BitmapFactory.decodeResource(getResources(),R.mipmap.blue_soldier);      armyBlue = Bitmap.createScaledBitmap(armyBlue, screenWidth / 15 , screenHeight / 15, false);
        armyYellow = BitmapFactory.decodeResource(getResources(),R.mipmap.yellow_soldier);  armyYellow = Bitmap.createScaledBitmap(armyYellow, screenWidth / 15 , screenHeight / 15, false);
        armyGreen = BitmapFactory.decodeResource(getResources(),R.mipmap.green_soldier);    armyGreen = Bitmap.createScaledBitmap(armyGreen, screenWidth / 15 , screenHeight / 15, false);
        landKaart = BitmapFactory.decodeResource(getResources(), R.mipmap.provincieskaart);

        active_game_id = db.getActiveGameID();
        active_player_id = Integer.parseInt(db.currentPlayer(active_game_id,"ID"));

        // check of deze beurt een aanvalsbeurt is
        if (db.getPlayerStatus(active_game_id) == "aanval"){
            attackTurnBoolean = true;
        }
        // als het geen aanvalsbeurt is; haal dan het aantal legers op dat de speler mag zetten
        else {
            aantalLegers = db.armyToPlace(active_player_id);
        }
        // maak de landkaart aan
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
                if(attackTurnBoolean){
                    // als dit een aanvalsbeurt is:
                    db.initAttack("Zeeland", "NoordBrabant", active_game_id);
                    //db.initAttack(String attacker, String defender, int game_id)
                }
                else {
                    // als het geen aanvalsbeurt is:
                    Log.i("klik locatie", "" + me.getX() + "  " + me.getY());
                    if (aantalLegers > 0) {
                        zetLeger(intX, intY);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Geen legers mee bij te zetten!", Toast.LENGTH_SHORT).show();
                        // als er geen legers meer te plaatsen zijn, wacht dan 1 seconde en ga naar het volgende scherm
                        Intent i = new Intent(this, MovePhase2.class);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ie) {
                        }
                        startActivity(i);
                    }
                    break;
                }

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
            while (isItOK) {
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

                // definieer de cursor uit de database handler en loop door de records heen
                Cursor cursor = db.getSituation(active_game_id);
                for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    // bepaal de kleur van het leger voor deze rij
                    //Log.i("cursor: ", " player_id: " + cursor.getString(0) + " world: " + cursor.getString(1) + " country_name: " + cursor.getString(2) + " country_armies: " + cursor.getString(3));
                    switch (cursor.getString(0)) {
                        case "1":
                            countryArmy = armyRed;
                            break;
                        case "2":
                            countryArmy = armyBlue;
                            break;
                        case "3":
                            countryArmy = armyYellow;
                            break;
                        case "4":
                            countryArmy = armyGreen;
                            break;
                    }
                    // zet de coordinaten van het leger voor deze rij
                    for (int i=0; i <12;i++) {
                        // zet alleen iets neer als dat land ook van de actieve speler is
                        if (Objects.equals(cursor.getString(2), ProvincieStringNaam[i])) {
                            coordX = ProvinciesLijst[i].centerX();
                            coordY = ProvinciesLijst[i].centerY();
                        }
                    }
                    // teken het soldaatje met de juiste kleur op de juiste plaats
                    c.drawBitmap(countryArmy, coordX - (countryArmy.getWidth() / 2), coordY - (countryArmy.getHeight() / 2), null);
                    c.drawCircle(coordX - ScaleY(25), coordY - ScaleY(80), ScaleY(30), black);
                    c.drawCircle(coordX - ScaleX(25), coordY - ScaleX(80), ScaleX(28), white);
                    c.drawText(cursor.getString(3), coordX - (countryArmy.getWidth() / 2), coordY - (countryArmy.getHeight() / 2), black);
                    c.drawText(cursor.getString(3), coordX, coordY, white);
                }
                cursor.close();

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

    public void zetLeger (int x, int y) {
        // loop door de landen heen om te zien of dit land van de actieve speler is
        for (int i=0; i <12;i++) {
            // zet alleen iets neer als dat land ook van de actieve speler is
            if (db.isOwner(active_player_id, ProvincieStringNaam[i] , active_game_id)) {
                if (ProvinciesLijst[i].contains(x, y)) {
                    db.setCountryArmies(ProvincieStringNaam[i], 1, "PLUS", active_game_id);
                    aantalLegers--;
                    db.updateArmiesToPlace(active_player_id, 1, "-");
                }
            }
        }
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