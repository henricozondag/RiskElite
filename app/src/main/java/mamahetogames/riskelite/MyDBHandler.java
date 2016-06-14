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

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARDS_TABLE = "CREATE TABLE " +
                TABLE_CARDS + "("
                + COLUMN_KEY + " INTEGER PRIMARY KEY," + COLUMN_GAME_ID
                + " INTEGER," + COLUMN_PLAYER + " INTEGER," + COLUMN_TYPE
                + " INTEGER," + COLUMN_NUMBER
                + " INTEGER" + ")";
        db.execSQL(CREATE_CARDS_TABLE);

        Log.i("ONCREATE?", "JA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }

    public void createData(int game_id) {
    }

    /*
    public void addProduct(Product product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public Product findProduct(String productname) {
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            product = null;
        }
        db.close();
        return product;
    }
*/

    public void addRandomCard(int player) {
        int type = ran.nextInt(3) + 1;
        //String query = "update " + TABLE_CARDS + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " + 1 where " + COLUMN_PLAYER + " = " + player + " and " + COLUMN_TYPE + " = " + type;
        String query = "insert or replace into " + TABLE_CARDS + " set " + COLUMN_NUMBER + " = " + COLUMN_NUMBER + " + 1 where " + COLUMN_PLAYER + " = " + player + " and " + COLUMN_TYPE + " = " + type;

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

    public Cursor testKlas (int player) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor hop = db.rawQuery("Select type, number from cards where player = " + player, null);
        return hop;
    }

    public void playerCards (int player, String PACKAGE_NAME) {

        PlayerDetails playDetails = new PlayerDetails();

        SQLiteDatabase db = this.getWritableDatabase();
        int showCard = 0;
        //String PACKAGE_NAME = getApplicationContext().getPackageName();

        Cursor playerCards = db.rawQuery("Select type, number from cards where player = " + player, null);
        playerCards.moveToFirst();

        // for loop zo vaak als er kaarten zijn
        for (int n = 1; n <= playerCards.getCount(); n++) {

            //per type loop totdat alle kaarten er staan
            for (int cardNr = 1; cardNr <= playerCards.getInt(1); cardNr++) {

                //type van de kaart ophalen
                playDetails.cardType[player][showCard] = playerCards.getInt(0);
                int imgId = playDetails.getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + playDetails.cardType[player][showCard], null, null);
                playDetails.imageViewCard[showCard].setImageBitmap(playDetails.decodeSampledBitmapFromResource(playDetails.getResources(), imgId, 100, 100));
                playDetails.checkBoxCard[showCard].setVisibility(View.VISIBLE);
                showCard++;
            }
            playerCards.moveToNext();
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
