package mamahetogames.riskelite;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.i("ONCREATE?", "Voor aanroep in splash");

        MyDBHandler db = new MyDBHandler(this);

        ////SQLiteDatabase mydatabase = openOrCreateDatabase("riskElite2",MODE_PRIVATE,null);
        //mydatabase.execSQL("create table if not exists game(key INTEGER PRIMARY KEY, game_id int");

        //mydatabase.execSQL("create table if not exists settings(key INTEGER PRIMARY KEY, game_id int, name string, value string");
        //mydatabase.execSQL("insert or replace into settings (key, game_id, name, value) values (1,1,'armyCard','4')");

        //mydatabase.execSQL("create table if not exists countries (key INTEGER PRIMARY KEY, game_id int, country string, owner string, armies int");

        ////mydatabase.execSQL("create table if not exists cards(key INTEGER PRIMARY KEY, game_id int, player int, type int, number int)");
        ////mydatabase.execSQL("insert or replace into cards (key, game_id, player, type, number) values (1,1,1,1,0)");
        ////mydatabase.execSQL("insert or replace into cards (key, game_id, player, type, number) values (2,1,1,2,0)");
        ////mydatabase.execSQL("insert or replace into cards (key, game_id, player, type, number) values (3,1,1,3,0)");
        ////mydatabase.execSQL("insert or replace into cards (key, game_id, player, type, number) values (4,1,2,1,0)");
        ////mydatabase.execSQL("insert or replace into cards (key, game_id, player, type, number) values (5,1,2,2,0)");
        ////mydatabase.execSQL("insert or replace into cards (key, game_id, player, type, number) values (6,1,2,3,0)");
        //mydatabase.execSQL("create table if not exists players(naam string, tijd string, level int)");

        // Wacht 2500ms en ga door naar menu
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i;
                i = new Intent(SplashScreen.this, Menu.class);
                startActivity(i);
            }
        }, 25);
    }
}
