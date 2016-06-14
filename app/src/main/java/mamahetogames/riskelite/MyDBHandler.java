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

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "riskElite4";

    public static final String TABLE_CARDS = "cards";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_GAME_ID = "game_id";
    public static final String COLUMN_PLAYER = "player";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NUMBER = "number";

    /*public static final String TABLE_COUNTRY = "country";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_GAME_ID = "game_id";
    public static final String COLUMN_PLAYER = "player";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NUMBER = "number";
    */
    Random ran = new Random();

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("ONCREATE?", "Bovendesupert");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ONCREATE?", "in oncreate");

        String CREATE_CARDS_TABLE = "CREATE TABLE " +
                TABLE_CARDS + "("
                + COLUMN_KEY + " INTEGER PRIMARY KEY," + COLUMN_GAME_ID
                + " INTEGER," + COLUMN_PLAYER + " INTEGER," + COLUMN_TYPE
                + " INTEGER," + COLUMN_NUMBER
                + " INTEGER" + ")";
        db.execSQL(CREATE_CARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
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
                String query = "insert or replace into cards (key, game_id, player, type, number) values (" + key + ",1," + player + "," + type + ",0)";
                db.execSQL(query);
                key++;
            }
            player++;
        }
        db.close();
    }

    public Cursor getCards(int player) {
        String selectQuery = "Select type, number from cards where player = " + player;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playerCards = db.rawQuery(selectQuery, null);

        return playerCards;
    }

    public void addRandomCard(int player) {
        int type = ran.nextInt(3) + 1;
        String query = "update " + TABLE_CARDS + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " + 1 where " + COLUMN_PLAYER + " = " + player + " and " + COLUMN_TYPE + " = " + type;
        //String query = "insert or replace into " + TABLE_CARDS + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " + 1 where " + COLUMN_PLAYER + " = " + player + " and " + COLUMN_TYPE + " = " + type;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(query);
        db.close();
    }

    public void removeCards(int player, ArrayList<Integer> cardList) {

        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i <= 2; i++) {
            String query = "update cards set number = number -1 where player = " + player + " and type = " + cardList.get(i);
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
