package mamahetogames.riskelite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 21;
    private static final String DATABASE_NAME = "riskElite";

    private static final String TABLE_CARD = "card";
    public static final String COLUMN_ID = "_id";
    private static final String COLUMN_GAME_ID = "game_id";
    private static final String COLUMN_GAMEPLAYER = "gameplayer";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_NUMBER = "number";

    private static final String TABLE_GAME = "game";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATUS = "status";
    private static final String COLUMN_ATTACK_COUNTRY = "attack_country";
    private static final String COLUMN_DICE_ATTACK_1 = "dice_attack_1";
    private static final String COLUMN_DICE_ATTACK_2 = "dice_attack_2";
    private static final String COLUMN_DICE_ATTACK_3 = "dice_attack_3";
    private static final String COLUMN_DEFEND_COUNTRY = "defend_country";
    private static final String COLUMN_DICE_DEFEND_1 = "dice_defend_1";
    private static final String COLUMN_DICE_DEFEND_2 = "dice_defend_2";
    private static final String COLUMN_CARD = "card";

    private static final String TABLE_PLAYER = "player";
    private static final String COLUMN_TOTAL_ARMIES = "total_armies";
    private static final String COLUMN_PLACE_ARMIES = "place_armies";
    private static final String COLUMN_ARMIES_WON = "armies_won";
    private static final String COLUMN_ARMIES_LOST = "armies_lost";
    private static final String COLUMN_COUNTRIES_WON = "countries_won";
    private static final String COLUMN_COUNTRIES_LOST = "countries_lost";

    public static final String TABLE_GAME_MAP = "game_map";
    public static final String COLUMN_PLAYER_ID = "player_id";
    public static final String COLUMN_CONTINENT = "country_continent";
    public static final String COLUMN_COUNTRY_ARMIES = "country_armies";
    public static final String COLUMN_WORLD = "world";

    private static final String TABLE_SETTING = "setting";
    private static final String COLUMN_PARAMETER_NM = "parameter_nm";
    private static final String COLUMN_PARAMETER_VALUE = "parameter_value";

    private static final String TABLE_MAP = "map";
    private static final String COLUMN_COUNTRY_ID = "country_id";
    private static final String COLUMN_COUNTRY_NAME = "country_name";
    private static final String COLUMN_COUNTRY_CONTINENT = "country_continent";

    private static final String TABLE_NEIGHBOUR = "neighbour";
    private static final String COLUMN_COUNTRY_ID_2 = "country_id_2";

    private final Random ran = new Random();
    private int gameID, passArmies;
    private int armyToPlace;
    private int numberOfPlayers1;
    private int player_id;
    private int armies;
    private int country_id;
    private String parameter_value;
    private String currentPlayer1;
    private String status;
    private String name, playerName, checkCard1;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARD_TABLE = "CREATE TABLE " + TABLE_CARD + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY,"
                + COLUMN_GAME_ID            + " INTEGER,"
                + COLUMN_PLAYER_ID          + " INTEGER,"
                + COLUMN_TYPE               + " INTEGER,"
                + COLUMN_NUMBER             + " INTEGER)";
        db.execSQL(CREATE_CARD_TABLE);

        String CREATE_GAME_TABLE = "CREATE TABLE " + TABLE_GAME + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME               + " TEXT,"
                + COLUMN_STATUS             + " TEXT,"
                + COLUMN_ATTACK_COUNTRY     + " TEXT,"
                + COLUMN_DICE_ATTACK_1      + " TEXT,"
                + COLUMN_DICE_ATTACK_2      + " TEXT,"
                + COLUMN_DICE_ATTACK_3      + " TEXT,"
                + COLUMN_DEFEND_COUNTRY     + " TEXT,"
                + COLUMN_DICE_DEFEND_1      + " TEXT,"
                + COLUMN_DICE_DEFEND_2      + " TEXT,"
                + COLUMN_CARD               + " TEXT)";
        db.execSQL(CREATE_GAME_TABLE);

        String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYER + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY,"
                + COLUMN_GAME_ID            + " INTEGER,"
                + COLUMN_NAME               + " TEXT,"
                + COLUMN_GAMEPLAYER         + " INTEGER,"
                + COLUMN_STATUS             + " TEXT,"
                + COLUMN_TOTAL_ARMIES       + " INTEGER,"
                + COLUMN_PLACE_ARMIES       + " INTEGER,"
                + COLUMN_ARMIES_WON         + " INTEGER,"
                + COLUMN_ARMIES_LOST        + " INTEGER,"
                + COLUMN_COUNTRIES_WON      + " INTEGER,"
                + COLUMN_COUNTRIES_LOST     + " INTEGER)";
        db.execSQL(CREATE_PLAYER_TABLE);

        String CREATE_SETTING_TABLE = "CREATE TABLE " + TABLE_SETTING + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY,"
                + COLUMN_GAME_ID            + " INTEGER,"
                + COLUMN_PARAMETER_NM       + " TEXT,"
                + COLUMN_PARAMETER_VALUE    + " TEXT)";
        db.execSQL(CREATE_SETTING_TABLE);

        String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_GAME_MAP + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY,"
                + COLUMN_GAME_ID            + " INTEGER,"
                + COLUMN_PLAYER_ID          + " INTEGER,"
                + COLUMN_WORLD              + " TEXT,"
                + COLUMN_COUNTRY_ID         + " INTEGER,"
                + COLUMN_COUNTRY_NAME       + " TEXT,"
                + COLUMN_CONTINENT          + " TEXT,"
                + COLUMN_COUNTRY_ARMIES     + " INTEGER)";
        db.execSQL(CREATE_COUNTRY_TABLE);

        String CREATE_WORLD_TABLE = "CREATE TABLE " + TABLE_MAP + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY,"
                + COLUMN_WORLD              + " TEXT,"
                + COLUMN_COUNTRY_ID         + " INTEGER,"
                + COLUMN_COUNTRY_NAME       + " TEXT,"
                + COLUMN_COUNTRY_CONTINENT  + " TEXT)";
        db.execSQL(CREATE_WORLD_TABLE);

        String CREATE_NEIGHBOUR_TABLE = "CREATE TABLE " + TABLE_NEIGHBOUR + "("
                + COLUMN_ID                 + " INTEGER PRIMARY KEY,"
                + COLUMN_COUNTRY_ID         + " INTEGER,"
                + COLUMN_COUNTRY_ID_2       + " INTEGER)";
        db.execSQL(CREATE_NEIGHBOUR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME_MAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEIGHBOUR);
        onCreate(db);
    }

    public void initWorlds() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "insert or replace into " + TABLE_MAP + "(" + COLUMN_ID + " , " + COLUMN_WORLD + " , " + COLUMN_COUNTRY_ID + " , " + COLUMN_COUNTRY_NAME + " , " + COLUMN_COUNTRY_CONTINENT + " ) " +
                "values ('1','Nederland','1','NoordBrabant','ZuidNederland')," +
                "('2','Nederland','2','Zeeland','ZuidNederland')," +
                "('3','Nederland','3','Limburg','ZuidNederland')," +
                "('4','Nederland','4','NoordHolland','WestNederland')," +
                "('5','Nederland','5','ZuidHolland','WestNederland')," +
                "('6','Nederland','6','Utrecht','WestNederland')," +
                "('7','Nederland','7','Gelderland','OostNederland')," +
                "('8','Nederland','8','Overijssel','OostNederland')," +
                "('9','Nederland','9','Flevoland','OostNederland')," +
                "('10','Nederland','10','Groningen','NoordNederland')," +
                "('11','Nederland','11','Friesland','NoordNederland')," +
                "('12','Nederland','12','Drenthe','NoordNederland')";
        db.execSQL(query);
    }

    public void initNeighbours() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "insert or replace into " + TABLE_NEIGHBOUR + "(" + COLUMN_ID + " , " + COLUMN_COUNTRY_ID + " , " + COLUMN_COUNTRY_ID_2 + " ) " +
                "values (1,1,2),(2,1,3),(3,1,5),(4,1,7)," +
                "(5,2,5)," +
                "(6,3,7)," +
                "(7,4,5),(8,4,6),(9,4,9),(10,4,11)," +
                "(11,5,6),(12,5,7)," +
                "(13,6,7),(14,6,9)," +
                "(15,7,8),(16,7,9)," +
                "(17,8,9),(18,8,11),(19,8,12)," +
                "(20,9,11),(21,9,12)," +
                "(22,10,11),(23,10,12)," +
                "(24,11,12)";
        db.execSQL(query);
    }

    private void initCards(int players, int game_id) {
        int player = 1;
        SQLiteDatabase db = this.getWritableDatabase();

        // selecteer player_id van de speler die het land krijgt
        player_id = getPlayerID(player, game_id);

        // Voor elke speler, drie kaarten inserten/updaten naar aantal:0
        for (int i=0; i < players; i++) {
            for (int j=0; j < 3; j++) {
                int type = j+1;
                String query = "insert or replace into " + TABLE_CARD + "( " + COLUMN_GAME_ID + " , " + COLUMN_PLAYER_ID + " , " + COLUMN_TYPE + " , " + COLUMN_NUMBER + " ) values ( " + game_id + " , " + player_id + " , " + type + ",0)";
                Log.i("insertcardsplayers", query);
                db.execSQL(query);
            }
            player++;
            player_id = getPlayerID(player, game_id);
        }
        db.close();
    }

    public void startGame (String name, String armyCards, String players, String world) {
        //insert nieuwe game
        int gameID = newGame(name,world);

        //insert het aantal cards per kaartruil
        setParameter("armyCard",Integer.valueOf(armyCards),gameID,"insert");
        //setCardArmy(armyCards,gameID);

        //insert het aantal players
        Integer x = Integer.valueOf(players);
        SQLiteDatabase db = this.getWritableDatabase();
        // Tijdelijk toegevoegd dat elke speler 2 armies mag plaatsen (Voor Thomas zijn code)
        for (int i = 0;i < x; i++) {
            String query = "insert into " + TABLE_PLAYER +
                    "(" + COLUMN_GAME_ID    + " , " + COLUMN_NAME + "   , " + COLUMN_GAMEPLAYER + " , " + COLUMN_STATUS + " , " + COLUMN_PLACE_ARMIES + "   , " + COLUMN_ARMIES_LOST + "    , " + COLUMN_ARMIES_WON + " , " + COLUMN_COUNTRIES_LOST + "    , " + COLUMN_COUNTRIES_WON + ") values " +
                    "(" + gameID            + " , 'leeg'                , " + (i+1) + "             , 'resting'             , 2                             , 0                             , 0                         , 0                             , 0)";
            Log.i("insertaantalplayers", query);
            db.execSQL(query);
        }

        // landen verdelen over de spelers
        divideCountries(gameID, players);

        //Per speler kaarten initialiseren
        initCards(Integer.valueOf(players), gameID);
    }

    private void divideCountries(int game_id, String players) {

        // conceptje zodat in ieder geval alle countries een eigenaar hebben. Kan natuurlijk veel beter worden gemaakt.
        SQLiteDatabase db = this.getWritableDatabase();
        //kies de eerste speler die een land krijgt
        int startPlayer =  ran.nextInt(Integer.valueOf(players)) + 1;

        // selecteer alle id's van de landen die in deze wereld zitten
        String query = "select " + COLUMN_ID + " from " + TABLE_GAME_MAP + " where " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("allecountries", query);
        Cursor countries = db.rawQuery(query, null);

        // for loop door de spelers heen totdat de cursor met alle countries op is
        for (countries.moveToFirst(); !countries.isAfterLast(); countries.moveToNext()) {

            // selecteer player_id van de speler die het land krijgt
            player_id = getPlayerID(startPlayer, game_id);

            String query3 = "update " + TABLE_GAME_MAP + " set " + COLUMN_PLAYER_ID + " = " +  player_id + ", " + COLUMN_COUNTRY_ARMIES + " = 3 where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_ID + " = " + countries.getInt(0);
            Log.i("updatecountryplayerid", query3);
            db.execSQL(query3);

            if (startPlayer == Integer.valueOf(players)) {
                startPlayer = 1;            }
            else
                startPlayer = startPlayer + 1;
        }
        countries.close();
    }

    public String checkAttackCard (int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select " + COLUMN_CARD + " from " + TABLE_GAME + " where " +  COLUMN_ID + " = " + game_id;
        Log.i("checkAttackCard", query);
        Cursor checkCard = db.rawQuery(query, null);

        if (checkCard.moveToFirst())
        {
            checkCard1 = checkCard.getString(0);
        }
        checkCard.close();
        return checkCard1;
    }

    public void setAttackCard (int game_id, String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update " + TABLE_GAME + " set " + COLUMN_CARD + " = '" +  status + "' where " + COLUMN_ID + " = " + game_id;
        Log.i("setAttackCard", query);
        db.execSQL(query);
    }

    public void setPlayerStatus(int game_id, String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update " + TABLE_PLAYER + " set " + COLUMN_STATUS + " = '" +  status + "' where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_STATUS + " <> 'resting'";
        Log.i("setplayerstatus", query);
        db.execSQL(query);
    }

    private int getPlayerID(int gameplayer, int game_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + COLUMN_ID + " from " + TABLE_PLAYER + " where " + COLUMN_GAMEPLAYER + " = " + gameplayer + " and " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("player_idopgameplayer", query);
        Cursor playerId = db.rawQuery(query, null);

        if (playerId.moveToFirst())
        {
            player_id = playerId.getInt(0);
        }
        playerId.close();
        return player_id;
    }

    public void loadGame (int gameID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update " + TABLE_GAME + " set " + COLUMN_STATUS + " = 'active' where " + COLUMN_ID + " = " + gameID;
        Log.i("loadgame", query);
        db.execSQL(query);
    }

    public String getPlayerStatus(int gameID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query2 = "select " + COLUMN_STATUS + " from " + TABLE_PLAYER + " where " + COLUMN_GAME_ID + " = " + gameID + " and " + COLUMN_STATUS + " <> 'resting'";
        Log.i("statusPlayer", query2);
        Cursor statusPlayer = db.rawQuery(query2, null);

        if (statusPlayer.moveToFirst())
        {
            status = statusPlayer.getString(0);
        }
        statusPlayer.close();
        return status;
    }

    public void startPlayer(int gameID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select count(" + COLUMN_NAME + ") from " + TABLE_PLAYER + " where " + COLUMN_GAME_ID + " = " + gameID;
        Log.i("numberofplayers", query);
        Cursor numberOfPlayers = db.rawQuery(query, null);

        if (numberOfPlayers.moveToFirst())
        {
            numberOfPlayers1 = numberOfPlayers.getInt(0);
        }

        numberOfPlayers.close();

        int startPlayer =  ran.nextInt(numberOfPlayers1) + 1;

        String query2 = "update " + TABLE_PLAYER + " set " + COLUMN_STATUS + " = 'active' where " + COLUMN_GAMEPLAYER + " = " + startPlayer + " and " + COLUMN_GAME_ID + " = " + gameID;
        Log.i("updatestartplayer", query2);
        db.execSQL(query2);
    }

    public Cursor getWorlds () {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select distinct(" + COLUMN_WORLD + ") from " + TABLE_MAP;

        return db.rawQuery(query, null);
    }

    public Cursor getCountries (int game_id, int player_id, String type) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        if (type.equals("owner")) {
            query = "select " + COLUMN_COUNTRY_NAME + " from " + TABLE_GAME_MAP + " where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_PLAYER_ID + " = " + player_id;
        } else
            query = "select " + COLUMN_COUNTRY_NAME + " from " + TABLE_GAME_MAP + " where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_PLAYER_ID + " <> " + player_id;
        Log.i("getcountries",query);

        return db.rawQuery(query, null);
    }

    public int getCountryArmies (String country_name, int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "select " + COLUMN_COUNTRY_ARMIES + " from " + TABLE_GAME_MAP + " where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_COUNTRY_NAME + " = '" + country_name + "'";
        Log.i("getcountryarmies",query);
        Cursor numberOfArmies = db.rawQuery(query, null);

        if (numberOfArmies.moveToFirst())
        {
            armies = numberOfArmies.getInt(0);
        }
        numberOfArmies.close();
        return armies;
    }

    public int numberOfPlayers(int game_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "select max(" + COLUMN_GAMEPLAYER + ") from " + TABLE_PLAYER + " where " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("numberofplayers",query);
        Cursor numberOfPlayers = db.rawQuery(query, null);

        if (numberOfPlayers.moveToFirst())
        {
            numberOfPlayers1 = numberOfPlayers.getInt(0);
        }
        numberOfPlayers.close();
        return numberOfPlayers1;
    }

    public void setPlayerName(int game_id, int gameplayer, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "update " + TABLE_PLAYER + " set " + COLUMN_NAME + " = '" + name + "' where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_GAMEPLAYER + " = " + gameplayer;
        Log.i("setPlayerName",query);

        db.execSQL(query);
        db.close();
    }

    public String currentPlayer(int game_id, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        if (Objects.equals(type, "name")) {
            query = "select " + COLUMN_NAME + " from " + TABLE_PLAYER + " where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_STATUS + " <> 'resting'";
        }
        else if (Objects.equals(type, "gameplayer")) {
            query = "select " + COLUMN_GAMEPLAYER + " from " + TABLE_PLAYER + " where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_STATUS + " <> 'resting'";
        }
        else if (Objects.equals(type, "status")) {
            query = "select " + COLUMN_STATUS + " from " + TABLE_PLAYER + " where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_STATUS + " <> 'resting'";
        }
        else
            query = "select " + COLUMN_ID + " from " + TABLE_PLAYER + " where " + COLUMN_GAME_ID + " = " + game_id + " and " + COLUMN_STATUS + " <> 'resting'";

        Cursor currentPlayer = db.rawQuery(query, null);

        if (currentPlayer.moveToFirst())
        {
            currentPlayer1 = currentPlayer.getString(0);
        }
        currentPlayer.close();
        Log.i("currentplayer", query);
        Log.i("currentgameplayer", currentPlayer1);
        return currentPlayer1;
    }

    public String getPlayerName(int game_id, int player_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select " + COLUMN_NAME + " from " + TABLE_PLAYER + " where " + COLUMN_ID + " = " + player_id + " and " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("playerName", query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            playerName = cursor.getString(0);
        }
        cursor.close();
        return playerName;

    }


    public void nextPlayer(int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int gamePlayer = Integer.parseInt(currentPlayer(game_id,"gameplayer"));
        int numberOfPlayers = numberOfPlayers(game_id);
        int nextGamePlayer;

        // Huidige speler resting maken
        String query = "update " + TABLE_PLAYER + " set " + COLUMN_STATUS + " = 'resting' where " + COLUMN_STATUS + " != 'resting' and " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("GameplayerResting",query);
        db.execSQL(query);

        // Volgende speler bepalen
        if (gamePlayer == numberOfPlayers) {
            nextGamePlayer = 1;
        }
        else
            nextGamePlayer = gamePlayer + 1;

        // Volgende speler actief maken
        String query2 = "update " + TABLE_PLAYER + " set " + COLUMN_STATUS + " = 'active' where " + COLUMN_GAMEPLAYER + " = " + nextGamePlayer + " and " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("GameplayerActive",query2);
        db.execSQL(query2);
    }

    public boolean attackerThrown(int game_id) {
        boolean result = false;
        String query = "select * from " + TABLE_GAME + " where " + COLUMN_DICE_ATTACK_1 + " is not null and " + COLUMN_ID + " = " + game_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("attackerthrown", query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result = true;
        }
        cursor.close();
        return result;
    }

    public void pauseGames() {
        SQLiteDatabase db = this.getWritableDatabase();

        //zet alle actieve games naar pause moet eigenlijk naar onicreate van menu (alleen het op pauze zetten)
        String query4 = "update " + TABLE_GAME + " set " + COLUMN_STATUS + " = 'pause' where " + COLUMN_STATUS + " = 'active' ";
        Log.i("updateGames", query4);
        db.execSQL(query4);
    }

    private int newGame(String name, String world) {
        SQLiteDatabase db = this.getWritableDatabase();

        //insert de nieuwe game met status active
        String query = "insert or replace into " + TABLE_GAME + " ( " + COLUMN_NAME + " , " + COLUMN_STATUS + " ) values ('" + name + "', 'active')";
        Log.i("newGameGame", query);
        db.execSQL(query);

        //haal het gameID op
        int gameID = getActiveGameID();

        //Stop alle bij de wereld horende landen in een cursor
        String query2 = "select " + COLUMN_COUNTRY_NAME + "," + COLUMN_COUNTRY_CONTINENT + "," + COLUMN_COUNTRY_ID + " from " + TABLE_MAP + " where " + COLUMN_WORLD + " = '" + world +  "'";
        Log.i("cursorvoorcountryinsert", query2);
        Cursor country = db.rawQuery(query2, null);

        //Doorloop de cursor met landen en stop ze 1 voor 1 in de country tabel
        for(country.moveToFirst(); !country.isAfterLast(); country.moveToNext()) {
            String query3 = "insert or replace into " + TABLE_GAME_MAP + " ( " + COLUMN_GAME_ID + " , " + COLUMN_WORLD + " , " + COLUMN_COUNTRY_ID + " , " + COLUMN_COUNTRY_NAME + " , " + COLUMN_CONTINENT + ") values (" + gameID + ", '" + world + "', " +  country.getString(2) + ", '" + country.getString(0) + "' ,'" + country.getString(1) +"' )";
            Log.i("newGameCountry", query3);
            db.execSQL(query3);
        }
        country.close();

        return gameID;
    }

    public int getActiveGameID() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + COLUMN_ID + " from " + TABLE_GAME + " where " + COLUMN_STATUS + " = 'active'";
        Log.i("getMaxGameID", query);
        Cursor game_key = db.rawQuery(query, null);

        if (game_key.moveToFirst())
        {
            gameID = game_key.getInt(0);
        }
        game_key.close();
        return gameID;
    }

    public Cursor getCards(int player_id) {
        String selectQuery = "Select " + COLUMN_TYPE + " , " + COLUMN_NUMBER + " from " + TABLE_CARD + " where " + COLUMN_PLAYER_ID + " = " + player_id;
        Log.i("getCards", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery(selectQuery, null);
    }

    public int armyToPlace(int player_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + COLUMN_PLACE_ARMIES + " from " + TABLE_PLAYER + " where " + COLUMN_ID + " = " + player_id;
        Log.i("placearmies", query);
        Cursor army = db.rawQuery(query, null);

        if (army.moveToFirst())
        {
            armyToPlace = army.getInt(0);
        }
        army.close();
        return armyToPlace;
    }

    public void updateArmiesToPlace(int player_id, int number, String type) {

        String query = "update " + TABLE_PLAYER + " set " + COLUMN_PLACE_ARMIES + " = " + COLUMN_PLACE_ARMIES + type + number + " where " + COLUMN_ID + " = " + player_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("updateArmyToPlace",query);

        db.execSQL(query);
    }

    public void addRandomCard(int player_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int type = ran.nextInt(3) + 1;
        String query = "update " + TABLE_CARD + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " + 1 where " + COLUMN_PLAYER_ID + " = " + player_id + " and " + COLUMN_TYPE + " = " + type;
        Log.i("addrandomcard",query);

        db.execSQL(query);
    }

    public int countCards(int player_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int cards = 0;
        String query = "select sum("+ COLUMN_NUMBER + ") from " + TABLE_CARD + " where " + COLUMN_PLAYER_ID + " = " + player_id;
        Log.i("countCards", query);
        Cursor nrCards = db.rawQuery(query, null);

        if (nrCards.moveToFirst())
        {
            cards = nrCards.getInt(0);
        }
        nrCards.close();
        return cards;
    }

    public String getParameter(String parameter_nm, int gameID) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select "+ COLUMN_PARAMETER_VALUE + " from " + TABLE_SETTING + " where " + COLUMN_PARAMETER_NM + " = '" + parameter_nm + "' and " + COLUMN_GAME_ID + " = " + gameID;
        Log.i("getparameter", query);
        Cursor parameterValue = db.rawQuery(query, null);

        if (parameterValue.moveToFirst())
        {
            parameter_value = parameterValue.getString(0);
        }
        parameterValue.close();
        return parameter_value;
    }

    public void setParameter(String parameter_nm, int parameter_value, int gameID, String action) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        if (Objects.equals(action, "insert")) {
            query = "insert or replace into " + TABLE_SETTING + " ( " + COLUMN_GAME_ID + " , " + COLUMN_PARAMETER_NM + " , " + COLUMN_PARAMETER_VALUE + " ) values (" + gameID + ", '" + parameter_nm + "', " + parameter_value + ")";
        } else {
            //update parameter
            query = "update " + TABLE_SETTING + " set " + COLUMN_PARAMETER_VALUE + " = " + parameter_value + " where " + COLUMN_GAME_ID + " = " + gameID + " and " + COLUMN_PARAMETER_NM + " = '" + parameter_nm + "'";
        }
        Log.i("insert or update", query);
        db.execSQL(query);
    }

    public void removeCards(int player_id, ArrayList<Integer> cardList) {

        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i <= 2; i++) {
            String query = "update " + TABLE_CARD + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " -1 where " + COLUMN_PLAYER_ID + " = " + player_id + " and " + COLUMN_TYPE + " = " + cardList.get(i);
            Log.i("removeCards", query);
            db.execSQL(query);
        }
        db.close();
    }

    //geeft een cursor terug met alle games. Gemaakt voor de spinner om de games te laden.
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

    private int getCountryId(String name, int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select " + COLUMN_COUNTRY_ID + " from " + TABLE_GAME_MAP + " where " + COLUMN_COUNTRY_NAME + " = '" + name + "' and " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("getcountryid", query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            country_id = cursor.getInt(0);
        }
        cursor.close();
        return country_id;
    }

    // te gebruiken bij het aanklikken van het land wat aangevallen wordt.
    // Bij true: Aanval kan doorgaan.
    // Bij false: Melding geven dat land niet grenst aan aanvallend land
    public boolean isNeighbour(String attacker, String defender, int game_id) {
        boolean result = false;
        int attack_country = getCountryId(attacker, game_id);
        int defend_country = getCountryId(defender, game_id);

        String query = "select * from " + TABLE_NEIGHBOUR + " where "
                + COLUMN_COUNTRY_ID + " = " + attack_country + " and " + COLUMN_COUNTRY_ID_2 + " = " + defend_country + " OR "
                + COLUMN_COUNTRY_ID_2 + " = " + attack_country + " and " + COLUMN_COUNTRY_ID + " = " + defend_country;
        Log.i("isneighbour", query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result = true;
        }
        cursor.close();
        return result;
    }

    // te gebruiken als een land aangeklikt wordt.
    // Bij true: Dan mag het een aanval land zijn
    // Bij false: Dan mag het een verdedig land zijn
    public boolean isOwner(int player_id, String country_name, int game_id) {
        boolean result = false;
        String query = "select * from " + TABLE_GAME_MAP + " where " + COLUMN_PLAYER_ID + " = " + player_id + " and " + COLUMN_COUNTRY_NAME + " = '" + country_name + "' and " + COLUMN_GAME_ID + " = " + game_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("isowner", query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result = true;
        }
        cursor.close();
        return result;
    }

    public void initAttack(String attacker, String defender, int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update " + TABLE_GAME + " set " + COLUMN_ATTACK_COUNTRY + " = '" + attacker + "', " + COLUMN_DEFEND_COUNTRY + " = '" + defender+ "' where " + COLUMN_ID + " = " + game_id;
        Log.i("initAttack", query);
        db.execSQL(query);
    }

    // type = PLUS voor optellen en MIN voor aftrekken!
    public void setCountryArmies(String country_name, int number, String type, int game_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        if (Objects.equals(type, "PLUS")) {
            query = "update " + TABLE_GAME_MAP + " set " + COLUMN_COUNTRY_ARMIES + " = " + COLUMN_COUNTRY_ARMIES + " + " + number + " where " + COLUMN_COUNTRY_NAME + " = '" + country_name + "' and " + COLUMN_GAME_ID + " = " + game_id;
            Log.i("PLUS", query);
        }
        else {
            query = "update " + TABLE_GAME_MAP + " set " + COLUMN_COUNTRY_ARMIES + " = " + COLUMN_COUNTRY_ARMIES + " - " + number + " where " + COLUMN_COUNTRY_NAME + " = '" + country_name + "' and " + COLUMN_GAME_ID + " = " + game_id;
            Log.i("MIN", query);
        }
        db.execSQL(query);
    }

    public String getGameCountry(String type, int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String country = type + "_COUNTRY";

        String query = "select " + country + " from " + TABLE_GAME + " where " + COLUMN_ID + " = " + game_id;
        Log.i("getGameCountry", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }

    // waarbij typ: ATTACK or DEFEND, diceNr = gegooide ogen, nr welke dobbelsteen
    public void setDice(String type, int diceNr, int nr, int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        String column = "DICE_" + type + "_" + (nr+1);

        if (Objects.equals(type, "DEFEND")) {
            query = "update " + TABLE_GAME + " set " + column + " = " + diceNr + " where " + COLUMN_ID + " = " + game_id;
            Log.i("setDefendDice", query);
        }
        else {
            query = "update " + TABLE_GAME + " set " + column + " = " + diceNr + " where " + COLUMN_ID + " = " + game_id;
            Log.i("setAttackDice", query);
        }
        db.execSQL(query);
    }

    public void resetDice (int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update " + TABLE_GAME + " set "
                + COLUMN_DICE_ATTACK_1 + " = null, "
                + COLUMN_DICE_ATTACK_2 + " = null, "
                + COLUMN_DICE_ATTACK_3 + " = null, "
                + COLUMN_DICE_DEFEND_1 + " = null, "
                + COLUMN_DICE_DEFEND_2 + " = null " +
                " where " + COLUMN_ID + " = " + game_id;
        Log.i("resetDice", query);
        db.execSQL(query);
    }

    public void resetCountries (int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update " + TABLE_GAME + " set "
                + COLUMN_ATTACK_COUNTRY + " = null, "
                + COLUMN_DEFEND_COUNTRY + " = null " +
                " where " + COLUMN_ID + " = " + game_id;
        Log.i("resetCountries", query);
        db.execSQL(query);
    }

    public boolean diceThrown(int game_id, int dice) {
        boolean thrown = false;
        String column = "DICE_ATTACK_" + dice;
        String query = "select " + column + " from " + TABLE_GAME + " where " + COLUMN_ID + " = " + game_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("is thrown", query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            thrown = true;
        }
        cursor.close();
        return thrown;
    }

    public int getDiceValue (int game_id, int dice) {
        SQLiteDatabase db = this.getWritableDatabase();

        int value = 0;
        String column = "DICE_ATTACK_" + dice;
        String query = "select " + column + " from " + TABLE_GAME + " where " + COLUMN_ID + " = " + game_id;
        Log.i("getGameCountry", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            value = cursor.getInt(0);
        }
        cursor.close();
        return value;
    }

    // deze cursor haalt de 'situatie' op waarin de landkaart verkeerd: hoeveel legers van welke spelers staan op welk landje?
    public Cursor getSituation(int game_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "select " + COLUMN_PLAYER_ID + "," + COLUMN_WORLD + "," + COLUMN_COUNTRY_NAME + "," + COLUMN_COUNTRY_ARMIES + " from " + TABLE_GAME_MAP  + " where " + COLUMN_GAME_ID + " = " + gameID;
        String query = "select play." + COLUMN_GAMEPLAYER + ", gmap." + COLUMN_WORLD + ", gmap." + COLUMN_COUNTRY_NAME + ",gmap." + COLUMN_COUNTRY_ARMIES +
                " from " + TABLE_GAME_MAP + " gmap," + TABLE_PLAYER + " play" +
                " where gmap." + COLUMN_GAME_ID + " = " + game_id +
                " and gmap." + COLUMN_PLAYER_ID + " = play." + COLUMN_ID;
        //Log.i("currentsituation", query);
        return db.rawQuery(query, null);
    }

    //deze methode update het totaal aantal verloren / gewonnen legers van een specifieke speler
    public void updateArmyResult (int player_id, String lostWon, int number) {
        SQLiteDatabase db = this.getWritableDatabase();

        String column = "ARMIES_" + lostWon;

        String query = "update " + TABLE_PLAYER + " set "
                + column + " = " + column + " + " +  number +
                " where " + COLUMN_ID + " = " + player_id;
        Log.i("updatearmyresult", query);
        db.execSQL(query);
    }

    //deze methode update het totaal aantal verloren / gewonnen countries van een specifieke speler
    public void updateCountryResult (int player_id, String lostWon, int number) {
        SQLiteDatabase db = this.getWritableDatabase();

        String column = "COUNTRIES_" + lostWon;

        String query = "update " + TABLE_PLAYER + " set "
                + column + " = " + column + " + " +  number +
                " where " + COLUMN_ID + " = " + player_id;
        Log.i("updatecountryresult", query);
        db.execSQL(query);
    }

    //deze methode geeft het player_id terug van de owner van het land
    public int getCountryOwner(String countryname, int game_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + COLUMN_PLAYER_ID + " from " + TABLE_GAME_MAP + " where " + COLUMN_COUNTRY_NAME + " = '" + countryname + "' and " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("getCountryOwner", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            player_id = cursor.getInt(0);
        }
        cursor.close();
        return player_id;
    }

    //methode die als een speler past gaat uitrekenen hoeveel legers hij er bij krijgt
    public int passArmies(int player_id, int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select count(" + COLUMN_COUNTRY_NAME + ") from " + TABLE_GAME_MAP + " where " + COLUMN_PLAYER_ID + " = " + player_id + " and " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("passarmies", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            passArmies = cursor.getInt(0);
        }
        cursor.close();

        //double n = passArmies/3;
        //passArmies = (int) Math.round(n);
        passArmies = (int) Math.round(passArmies/3);
        if (passArmies < 3) {
            passArmies = 3;
        }
        return passArmies;
    }

    //updaten van een country owner
    public void updateCountryOwner (String country_name,int active_player, int game_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update " + TABLE_GAME_MAP + " set "
                + COLUMN_PLAYER_ID + " = " + active_player +
                " where " + COLUMN_COUNTRY_NAME + " = '" + country_name + "' and " + COLUMN_GAME_ID + " = " + game_id;
        Log.i("updateCountryOwner", query);
        db.execSQL(query);
    }
}
