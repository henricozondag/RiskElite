package mamahetogames.riskelite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "riskElite";

    public static final String TABLE_CARD = "card";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_GAME_KEY = "game_key";
    public static final String COLUMN_PLAYER = "player";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NUMBER = "number";

    public static final String TABLE_GAME = "game";
    public static final String COLUMN_NAME = "name";

    public static final String TABLE_PLAYER = "player";

    public static final String TABLE_COUNTRY = "country";
    public static final String COLUMN_PLAYER_KEY = "player_key";
    public static final String COLUMN_CONTINENT = "continent";

    public static final String TABLE_SETTING = "setting";
    public static final String COLUMN_PARAMETER_NM = "parameter_nm";
    public static final String COLUMN_PARAMETER_VALUE = "parameter_value";

    Random ran = new Random();

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARD_TABLE = "CREATE TABLE " +
                TABLE_CARD + "("
                + COLUMN_KEY + " INTEGER PRIMARY KEY," + COLUMN_GAME_KEY
                + " INTEGER," + COLUMN_PLAYER + " INTEGER," + COLUMN_TYPE
                + " INTEGER," + COLUMN_NUMBER
                + " INTEGER" + ")";
        db.execSQL(CREATE_CARD_TABLE);

        String CREATE_GAME_TABLE = "CREATE TABLE " +
                TABLE_GAME + "("
                + COLUMN_KEY + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT" + ")";
        db.execSQL(CREATE_GAME_TABLE);

        String CREATE_PLAYER_TABLE = "CREATE TABLE " +
                TABLE_PLAYER + "("
                + COLUMN_KEY + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT," + COLUMN_GAME_KEY + " INTEGER," + COLUMN_PLAYER
                + " INTEGER" + ")";
        db.execSQL(CREATE_PLAYER_TABLE);

        String CREATE_SETTING_TABLE = "CREATE TABLE " +
                TABLE_SETTING + "("
                + COLUMN_KEY + " INTEGER PRIMARY KEY," + COLUMN_GAME_KEY
                + " INTEGER," + COLUMN_PARAMETER_NM + " TEXT," + COLUMN_PARAMETER_VALUE
                + " TEXT" + ")";
        db.execSQL(CREATE_SETTING_TABLE);

        String CREATE_COUNTRY_TABLE = "CREATE TABLE " +
                TABLE_COUNTRY + "("
                + COLUMN_KEY + " INTEGER PRIMARY KEY," + COLUMN_GAME_KEY
                + " INTEGER," + COLUMN_PLAYER_KEY + " INTEGER," + COLUMN_NAME
                + " TEXT," + COLUMN_CONTINENT + " TEXT" + ")";
        db.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
        onCreate(db);
    }

    public void initCards(int players) {
        int key = 1;
        int player = 1;
        SQLiteDatabase db = this.getWritableDatabase();

        // Voor elke speler, drie kaarten inserten/updaten naar aantal:0
        for (int i=0; i < players; i++) {
            for (int j=0; j < 3; j++) {
                int type = j+1;
                String query = "insert or replace into card (key, game_key, player, type, number) values (" + key + ",1," + player + "," + type + ",0)";
                db.execSQL(query);
                key++;
            }
            player++;
        }
        db.close();
    }

//    public int newGame(String name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        int game;
//        // autoincrement testje als je de key niet meegeeft als value.
//        String query = "insert or replace into game (name) values ('" + name + "')";
//        db.execSQL(query);
//
//        String query2 = "select max(key) from game where name = '" + name + "'";
//        Cursor game_key = db.rawQuery(query2, null);
//
//        if (game_key.moveToFirst())
//        {
//            game = game_key.getInt(0);
//        }
//        return game;
//    }



    public Cursor getCards(int player) {
        String selectQuery = "Select type, number from card where player = " + player;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playerCards = db.rawQuery(selectQuery, null);

        return playerCards;
    }

    public void addRandomCard(int player) {
        int type = ran.nextInt(3) + 1;
        String query = "update " + TABLE_CARD + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " + 1 where " + COLUMN_PLAYER + " = " + player + " and " + COLUMN_TYPE + " = " + type;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(query);
        db.close();
    }

    public void removeCards(int player, ArrayList<Integer> cardList) {

        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i <= 2; i++) {
            String query = "update card set number = number -1 where player = " + player + " and type = " + cardList.get(i);
            db.execSQL(query);
        }
        db.close();
    }

    public boolean isNeighbour(int attacker, int defender) {
        boolean result = false;
        String query = "Select * FROM ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result = true;
        }
        db.close();
        return result;
    }
}
