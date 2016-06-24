package mamahetogames.riskelite;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "riskElite";

    public static final String TABLE_CARD = "card";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GAME_ID = "game_id";
    public static final String COLUMN_GAMEPLAYER = "gameplayer";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NUMBER = "number";

    public static final String TABLE_GAME = "game";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATUS = "status";

    public static final String TABLE_PLAYER = "player";

    public static final String TABLE_COUNTRY = "country";
    public static final String COLUMN_PLAYER_ID = "player_id";
    public static final String COLUMN_CONTINENT = "continent";
    public static final String COLUMN_WORLD = "world";
    public static final String TABLE_SETTING = "setting";
    public static final String COLUMN_PARAMETER_NM = "parameter_nm";
    public static final String COLUMN_PARAMETER_VALUE = "parameter_value";

    public static final String TABLE_WORLD = "world";
    public static final String COLUMN_COUNTRY_ID = "country_id";
    public static final String COLUMN_COUNTRY_NAME = "country_name";
    public static final String COLUMN_COUNTRY_CONTINENT = "country_continent";

    Random ran = new Random();
    int currentPlayer1, gameKey;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARD_TABLE = "CREATE TABLE " +
                TABLE_CARD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_GAME_ID
                + " INTEGER," + COLUMN_PLAYER_ID + " INTEGER," + COLUMN_TYPE
                + " INTEGER," + COLUMN_NUMBER
                + " INTEGER" + ")";
        db.execSQL(CREATE_CARD_TABLE);

        String CREATE_GAME_TABLE = "CREATE TABLE " +
                TABLE_GAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT," + COLUMN_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_GAME_TABLE);

        String CREATE_PLAYER_TABLE = "CREATE TABLE " +
                TABLE_PLAYER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT," + COLUMN_GAME_ID + " INTEGER," + COLUMN_GAMEPLAYER
                + " INTEGER," + COLUMN_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_PLAYER_TABLE);

        String CREATE_SETTING_TABLE = "CREATE TABLE " +
                TABLE_SETTING + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_GAME_ID
                + " INTEGER," + COLUMN_PARAMETER_NM + " TEXT," + COLUMN_PARAMETER_VALUE
                + " TEXT" + ")";
        db.execSQL(CREATE_SETTING_TABLE);

        String CREATE_COUNTRY_TABLE = "CREATE TABLE " +
                TABLE_COUNTRY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_GAME_ID
                + " INTEGER," + COLUMN_PLAYER_ID + " INTEGER," + COLUMN_WORLD + " TEXT," + COLUMN_NAME
                + " TEXT," + COLUMN_CONTINENT + " TEXT" + ")";
        db.execSQL(CREATE_COUNTRY_TABLE);

        String CREATE_WORLD_TABLE = "CREATE TABLE " +
                TABLE_WORLD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT," + COLUMN_COUNTRY_ID + " INTEGER," + COLUMN_COUNTRY_NAME
                + " TEXT," + COLUMN_COUNTRY_CONTINENT + " TEXT" + ")";
        db.execSQL(CREATE_WORLD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORLD);
        onCreate(db);
    }

    public void initWorlds() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "insert or replace into " + TABLE_WORLD + "(" + COLUMN_ID + " , " + COLUMN_NAME + " , " + COLUMN_COUNTRY_ID + " , " + COLUMN_COUNTRY_NAME + " , " + COLUMN_COUNTRY_CONTINENT + " ) " +
                "values (12, 'Nederland','1','Noord Brabant','Zuid Nederland')," +
                "(1, 'Nederland','2','Zeeland','Zuid Nederland')," +
                "(2, 'Nederland','3','Limburg','Zuid Nederland')," +
                "(3, 'Nederland','4','Noord Holland','West Nederland')," +
                "(4, 'Nederland','5','Zuid Holland','West Nederland')," +
                "(5, 'Nederland','6','Utrecht','West Nederland')," +
                "(6, 'Nederland','7','Gelderland','Oost Nederland')," +
                "(7, 'Nederland','8','Overijssel','Oost Nederland')," +
                "(8, 'Nederland','9','Flevoland','Oost Nederland')," +
                "(9, 'Nederland','10','Groningen','Noord Nederland')," +
                "(10, 'Nederland','11','Friesland','Noord Nederland')," +
                "(11, 'Nederland','12','Assen','Noord Nederland')";
        db.execSQL(query);
    }

    public void initCards(int players) {
        int key = 1;
        int player = 1;
        SQLiteDatabase db = this.getWritableDatabase();

        // Voor elke speler, drie kaarten inserten/updaten naar aantal:0
        for (int i=0; i < players; i++) {
            for (int j=0; j < 3; j++) {
                int type = j+1;
                String query = "insert or replace into " + TABLE_CARD + "( " + COLUMN_ID + " , " + COLUMN_GAME_ID + " , " + COLUMN_PLAYER_ID + " , " + COLUMN_TYPE + " , " + COLUMN_NUMBER + " ) values (" + key + ",1," + player + "," + type + ",0)";
                db.execSQL(query);
                key++;
            }
            player++;
        }
        db.close();
    }

    public void startGame (String name, String armyCards, String players, String world) {
        //insert nieuwe game
       int gameID = newGame(name,world);

        //insert het aantal cards per kaartruil
        setCardArmy(armyCards,gameID);

        //insert het aantal players
        Integer x = Integer.valueOf(players);
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0;i < x; i++) {
            String query = "insert into " + TABLE_PLAYER + "(" + COLUMN_GAME_ID + " , " + COLUMN_NAME + " , " + COLUMN_GAMEPLAYER + " , " + COLUMN_STATUS + " ) values (" + gameID + ",'leeg'," + (i+1) + ",'resting')";
            db.execSQL(query);
            Log.i("insertaantalplayers", query);
        }
    }

    public Cursor getWorlds () {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select distinct(" + COLUMN_NAME + ") from " + TABLE_WORLD;
        Cursor worlds = db.rawQuery(query, null);

        return worlds;
    }

    public int currentPlayer(int game_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + COLUMN_PLAYER_ID + " from " + TABLE_PLAYER + " where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_STATUS + " = active";
        Cursor currentPlayer = db.rawQuery(query, null);

        if (currentPlayer.moveToFirst())
        {
            currentPlayer1 = currentPlayer.getInt(0);
        }
        return currentPlayer1;
    }
//
//    public int nextPlayer(int game_id) {
//        int gameplayer = select gameplayer from player where game_id = game_id and status = active;
//        int nrPlayers = select max(gameplayer) from player where game_id = game_id;
//        if (gameplayer = nrPlayers) {
//            gameplayer = 1;
//            else gameplayer = gameplayer + 1;
//        }
//        int nextPlayer = select player_id from player where game_id = game_id and gameplayer = gameplayer;
//        update player set status = resting where game_id = game_id and status = active;
//        update player set status = active where game_id = game_id and gameplayer = gameplayer;
//
//        return player_id;
//    }

    public int newGame(String name, String world) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "insert or replace into " + TABLE_GAME + " ( " + COLUMN_NAME + " , " + COLUMN_STATUS + " ) values ('" + name + "', 'ACTIVE')";
        Log.i("newGameGame", query);
        db.execSQL(query);

        //haal het gameID op
        int gameID = getGameKey(name);


        String query2 = "select " + COLUMN_COUNTRY_NAME + "," + COLUMN_COUNTRY_CONTINENT + " from " + TABLE_WORLD + " where " + COLUMN_NAME + " = '" + world +  "'";
        Log.i("cursorvoorcountryinsert", query2);
        Cursor country = db.rawQuery(query2, null);


        for(country.moveToFirst(); !country.isAfterLast(); country.moveToNext()) {
            String query3 = "insert or replace into " + TABLE_COUNTRY + " ( " + COLUMN_GAME_ID + " , " + COLUMN_WORLD + " , " + COLUMN_NAME + " , " + COLUMN_CONTINENT + ") values (" + gameID + ", '" + world + "', '" + country.getString(0) + "' ,'" + country.getString(1) +"' )";
            Log.i("newGameCountry", query3);
            db.execSQL(query3);
        }
        country.close();

        return gameID;
    }

    public void setCardArmy(String armyCard, int gameID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "insert or replace into " + TABLE_SETTING + " ( " + COLUMN_GAME_ID + " , " + COLUMN_PARAMETER_NM + " , " + COLUMN_PARAMETER_VALUE + " ) values ('" + gameID + "', 'armyCard', " + armyCard + ")";
        Log.i("setCardArmy", query);
        db.execSQL(query);
    }

    public int getGameKey(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select max( " + COLUMN_ID + " ) from " + TABLE_GAME + " where " + COLUMN_NAME + " = '" + name + "'";
        Log.i("getGameKey", query);
        Cursor game_key = db.rawQuery(query, null);

        if (game_key.moveToFirst())
        {
            gameKey = game_key.getInt(0);
        }
        return gameKey;
    }

    // hier moet nog een aanpassing komen omdat we de kaarten ophalen adv het player_id ipv player. Dus moet nog een methode tussen gezet worden.
    public Cursor getCards(int player_id, int game_id) {
        String selectQuery = "Select " + COLUMN_TYPE + " , " + COLUMN_NUMBER + " from " + TABLE_CARD + " where " + COLUMN_PLAYER_ID + " = " + player_id + "and " + COLUMN_GAME_ID + " = " + game_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playerCards = db.rawQuery(selectQuery, null);

        return playerCards;
    }

    public void addRandomCard(int player) {
        int type = ran.nextInt(3) + 1;
        String query = "update " + TABLE_CARD + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " + 1 where " + COLUMN_GAMEPLAYER + " = " + player + " and " + COLUMN_TYPE + " = " + type;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(query);
        db.close();
    }

    public void removeCards(int player, ArrayList<Integer> cardList) {

        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i <= 2; i++) {
            String query = "update " + TABLE_CARD + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " -1 where " + COLUMN_GAMEPLAYER + " = " + player + " and " + COLUMN_TYPE + " = " + cardList.get(i);
            db.execSQL(query);
        }
        db.close();
    }

    public Cursor fetchAllGames() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.query(TABLE_GAME, new String[] {COLUMN_ID,
                        COLUMN_NAME, COLUMN_STATUS},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchGamesByName(String inputText) throws SQLException {
        Cursor mCursor = null;

        SQLiteDatabase db = this.getWritableDatabase();
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = db.query(TABLE_GAME, new String[] {COLUMN_ID,
                            COLUMN_NAME, COLUMN_STATUS},
                    null, null, null, null, null);

        }
        else {
            mCursor = db.query(TABLE_GAME, new String[] {COLUMN_ID,
                            COLUMN_NAME, COLUMN_STATUS},
                    COLUMN_NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    // te gebruiken bij het aanklikken van het land wat aangevallen wordt.
    // Bij true: Aanval kan doorgaan.
    // Bij false: Melding geven dat land niet grenst aan aanvallend land
    public boolean isNeighbour(int attacker, int defender) {
        boolean result = false;
        String query = "select * from ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result = true;
        }
        db.close();
        return result;
    }

    // te gebruiken als een land aangeklikt wordt.
    // Bij true: Dan mag het een aanval land zijn
    // Bij false: Dan mag het een verdedig land zijn
    public boolean isOwner(int player, int country_id) {
        boolean result = false;
        String query = "select * from " + TABLE_COUNTRY + " where " + COLUMN_PLAYER_ID + " = " + player + " and " + COLUMN_ID + " = " + country_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result = true;
        }
        return result;
    }
}
