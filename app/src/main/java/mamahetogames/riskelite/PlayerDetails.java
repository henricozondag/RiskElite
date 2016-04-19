package mamahetogames.riskelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlayerDetails extends AppCompatActivity implements View.OnClickListener{

    public ArrayList<Integer> cardList = new ArrayList<>();
    int player = 0, armieCard, plaatsLegers;
    // welke status heeft de speler? (phase1/2 of 3) ivm het wel of niet mogen ruilen van de kaarten
    String status;
    Random ran = new Random();
    TextView textViewAantalLegers, textViewPlaatsenLegers;
    Button buttonPlaatsenLegers, buttonMovePhase2, buttonRuilKaarten, buttonAddRandomCard;
    ImageView[] imageViewCard = new ImageView[10];
    CheckBox[] checkBoxCard = new CheckBox[10];
    int[][] cardType = new int[3][10];
    ImageView imageViewCardType1, imageViewCardType2, imageViewCardType3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        // Haal waarde op uit bundle die meegestuurd is bij opstarten scherm, dit is de speler waarvoor je de kaarten bekijkt.
        Bundle b = getIntent().getExtras();
        player = b.getInt("player");
        status = b.getString("status");

        buttonMovePhase2 = (Button) findViewById(R.id.buttonMovePhase2);
        buttonMovePhase2.setOnClickListener(this);
        buttonRuilKaarten = (Button) findViewById(R.id.buttonRuilKaarten);
        buttonRuilKaarten.setOnClickListener(this);
        buttonPlaatsenLegers = (Button) findViewById(R.id.buttonPlaatsenLegers);
        buttonPlaatsenLegers.setOnClickListener(this);
        buttonAddRandomCard = (Button) findViewById(R.id.buttonAddRandomCard);
        buttonAddRandomCard.setOnClickListener(this);

        // de kaarten
        imageViewCard[0] = (ImageView)findViewById(R.id.imageViewCard1);
        checkBoxCard[0] = (CheckBox)findViewById(R.id.checkBoxCard1);
        imageViewCard[1] = (ImageView)findViewById(R.id.imageViewCard2);
        checkBoxCard[1] = (CheckBox)findViewById(R.id.checkBoxCard2);
        imageViewCard[2] = (ImageView)findViewById(R.id.imageViewCard3);
        checkBoxCard[2] = (CheckBox)findViewById(R.id.checkBoxCard3);
        imageViewCard[3] = (ImageView)findViewById(R.id.imageViewCard4);
        checkBoxCard[3] = (CheckBox)findViewById(R.id.checkBoxCard4);
        imageViewCard[4] = (ImageView)findViewById(R.id.imageViewCard5);
        checkBoxCard[4] = (CheckBox)findViewById(R.id.checkBoxCard5);
        imageViewCard[5] = (ImageView)findViewById(R.id.imageViewCard6);
        checkBoxCard[5] = (CheckBox)findViewById(R.id.checkBoxCard6);
        imageViewCard[6] = (ImageView)findViewById(R.id.imageViewCard7);
        checkBoxCard[6] = (CheckBox)findViewById(R.id.checkBoxCard7);
        imageViewCard[7] = (ImageView)findViewById(R.id.imageViewCard8);
        checkBoxCard[7] = (CheckBox)findViewById(R.id.checkBoxCard8);
        imageViewCard[8] = (ImageView)findViewById(R.id.imageViewCard9);
        checkBoxCard[8] = (CheckBox)findViewById(R.id.checkBoxCard9);
        imageViewCard[9] = (ImageView)findViewById(R.id.imageViewCard10);
        checkBoxCard[9] = (CheckBox)findViewById(R.id.checkBoxCard10);

        //onzichtbare imageviews waarmee ik vergelijk (voor het verwijderen)
        imageViewCardType1 = (ImageView)findViewById(R.id.imageViewCardType1);
        imageViewCardType2 = (ImageView)findViewById(R.id.imageViewCardType2);
        imageViewCardType3 = (ImageView)findViewById(R.id.imageViewCardType3);

        String PACKAGE_NAME = getApplicationContext().getPackageName();
        int imgId1 = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card1", null, null);
        imageViewCardType1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId1, 100, 100));
        int imgId2 = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card2", null, null);
        imageViewCardType2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId2, 100, 100));
        int imgId3 = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card3", null, null);
        imageViewCardType3.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId3, 100, 100));

        textViewAantalLegers = (TextView)findViewById(R.id.textViewAantalLegers);
        textViewPlaatsenLegers =(TextView)findViewById(R.id.textViewPlaatsenLegers);

        // haal op welke kaarten er allemaal actief zijn voor de huidige speler
        loadSavedPreferences();
        laatKaartenZien();
    }

    //bitmap geneuzel
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public void laatKaartenZien () {

        // standaard beginnen met een leeg scherm
        for (int n = 0; n <= 9; n++) {
            checkBoxCard[n].setChecked(false);
            checkBoxCard[n].setVisibility(View.INVISIBLE);
            imageViewCard[n].setImageResource(0);
        }
        // showcard geeft aan welke van de 10 kaarten gevuld is
        int showCard = 0;
        //open database
        SQLiteDatabase mydatabase = openOrCreateDatabase("riskElite2", MODE_PRIVATE, null);

        // Ophalen van het type kaart en de hoeveelheid van dat type
        String PACKAGE_NAME = getApplicationContext().getPackageName();
        Cursor playerCards = mydatabase.rawQuery("Select type, number from cards where player = " + player, null);
        playerCards.moveToFirst();

        // for loop zo vaak als er kaarten zijn
        for (int n = 1; n <= playerCards.getCount(); n++) {
            Log.i("type", Integer.toString(playerCards.getInt(0)));
            Log.i("number", Integer.toString(playerCards.getInt(1)));
            Log.i("showcard", Integer.toString(showCard));

            //per type loop totdat alle kaarten er staan
            for (int cardNr = 1; cardNr <= playerCards.getInt(1); cardNr++) {

                //type van de kaart ophalen
                cardType[player][showCard] = playerCards.getInt(0);
                int imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + cardType[player][showCard], null, null);
                imageViewCard[showCard].setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                checkBoxCard[showCard].setVisibility(View.VISIBLE);
                showCard++;
            }
            playerCards.moveToNext();
        }
    }

    // methode om 2 bitmaps te vergelijken met elkaar. Gebruik ik om te kijken welke kaarten ik uit de database moet verwijderen
    boolean SameAs(Bitmap A, Bitmap B) {

        // Different types of image
        if(A.getConfig() != B.getConfig())
            return false;

        // Different sizes
        if (A.getWidth() != B.getWidth())
            return false;
        if (A.getHeight() != B.getHeight())
            return false;

        // Allocate arrays - OK because at worst we have 3 bytes + Alpha (?)
        int w = A.getWidth();
        int h = A.getHeight();

        int[] argbA = new int[w*h];
        int[] argbB = new int[w*h];

        A.getPixels(argbA, 0, w, 0, 0, w, h);
        B.getPixels(argbB, 0, w, 0, 0, w, h);

        // Alpha channel special check
        if (A.getConfig() == Bitmap.Config.ALPHA_8) {
            // in this case we have to manually compare the alpha channel as the rest is garbage.
            final int length = w * h;
            for (int i = 0 ; i < length ; i++) {
                if ((argbA[i] & 0xFF000000) != (argbB[i] & 0xFF000000)) {
                    return false;
                }
            }
            return true;
        }

        return Arrays.equals(argbA, argbB);
    }

    public void ruilKaarten() {

        int counter = 0;
        cardList.clear();
        for(int card=0; card<=9; card++) {
            // tellen hoeveel er zijn gecheckt
            if (checkBoxCard[card].isChecked()) {
                counter++;
                cardList.add(cardType[player][card]);
            }
        }
        //Bekijken welk soort setje is ingeleverd: (drie dezelfde? drie verschillende? fout?)
        if (counter == 3) {
            if (cardList.get(0) == cardList.get(1) && cardList.get(0) == cardList.get(2)) {
                Toast.makeText(PlayerDetails.this, "Heel goed, je hebt drie dezelfde aangevinkt!",  Toast.LENGTH_LONG).show();
                addArmies();
                removeCards();
            }
            else if (cardList.get(0) != cardList.get(1) && cardList.get(0) != cardList.get(2) && cardList.get(1) != cardList.get(2)) {
                Toast.makeText(PlayerDetails.this, "Heel goed, je hebt drie verschillende aangevinkt!",  Toast.LENGTH_LONG).show();
                addArmies();
                removeCards();
            }
            else {
                Toast.makeText(PlayerDetails.this, "Wow 3 kaarten, alleen helaas niet 3 verschillende of 3 dezelfde!",  Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(PlayerDetails.this, "Je moet 3 kaarten aanvinken!",  Toast.LENGTH_LONG).show();
        }
    }

    public void addArmies() {

        // geef het aantal legers wat geplaatst mag worden door aan variabele
        plaatsLegers = plaatsLegers + armieCard;

        // schermdingen
        textViewAantalLegers.setVisibility(View.VISIBLE);
        textViewAantalLegers.setText(Integer.toString(plaatsLegers));
        textViewPlaatsenLegers.setVisibility(View.VISIBLE);
        buttonPlaatsenLegers.setVisibility(View.VISIBLE);
        buttonMovePhase2.setVisibility(View.INVISIBLE);

        armieCard = armieCard + 2;
        savePreferences("armieCard", armieCard);
    }

    public void removeCards() {

        SQLiteDatabase mydatabase = openOrCreateDatabase("riskElite2", MODE_PRIVATE, null);
        //achterhalen welke kaarten aangevinkt waren en daarvan de status op 0 zetten
        //hierdoor veranderd het plaatje in een kruis en verdwijnt de checkbox onder het plaatje
        for(int card=0; card<=9; card++) {
            if (checkBoxCard[card].isChecked()) {
                Bitmap bitmap = ((BitmapDrawable)imageViewCard[card].getDrawable()).getBitmap();
                Bitmap bitmap1 = ((BitmapDrawable)imageViewCardType1.getDrawable()).getBitmap();
                Bitmap bitmap2 = ((BitmapDrawable)imageViewCardType2.getDrawable()).getBitmap();

                if (SameAs(bitmap, bitmap1) == true) {
                    Log.i("bitmap", "1");
                    Cursor cardType = mydatabase.rawQuery("Select number from cards where player = " + player + " and type = 1", null);
                    cardType.moveToFirst();
                    int number = cardType.getInt(0) - 1;
                    mydatabase.execSQL("update cards set number = " + number + " where player = " + player + " and type = 1");
                } else if (SameAs(bitmap, bitmap2) == true) {
                    Log.i("bitmap", "2");
                    Cursor cardType = mydatabase.rawQuery("Select number from cards where player = " + player + " and type = 2", null);
                    cardType.moveToFirst();
                    int number = cardType.getInt(0) - 1;
                    mydatabase.execSQL("update cards set number = " + number + " where player = " + player + " and type = 2");
                } else {
                    Log.i("bitmap", "3");
                    Cursor cardType = mydatabase.rawQuery("Select number from cards where player = " + player + " and type = 3", null);
                    cardType.moveToFirst();
                    int number = cardType.getInt(0) - 1;
                    mydatabase.execSQL("update cards set number = " + number + " where player = " + player + " and type = 3");
                }
            }
        }
        laatKaartenZien();
        mydatabase.close();
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        /*for(int card=0; card<=9; card++) {
            cardType[player][card] = sharedPreferences.getInt("card"+card, 1);
        }*/
        armieCard = sharedPreferences.getInt("armieCard", 4);
    }

    private void savePreferences(String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void addRandomCard(int player) {
        SQLiteDatabase mydatabase = openOrCreateDatabase("riskElite2", MODE_PRIVATE, null);
        int type = ran.nextInt(3) + 1;

        Log.i("type", Integer.toString(type));

        Cursor playerCards = mydatabase.rawQuery("Select number from cards where player = " + player + " and type = " + type, null);
        playerCards.moveToFirst();
        int number = playerCards.getInt(0);
        Log.i("number", Integer.toString(number));
        number++;
        Log.i("number++", Integer.toString(number));

        // insert voorbeeldje
        mydatabase.execSQL("update cards set number = " + number + " where player = " + player + " and type = " + type);
        mydatabase.close();
        laatKaartenZien();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase2:
                i = new Intent(this, MovePhase2.class);
                startActivity(i);
                break;
            case R.id.buttonRuilKaarten:
                ruilKaarten();
                break;
            case R.id.buttonPlaatsenLegers:
                //finish();   Dit is volgens mij niet nodig nu
                i = new Intent(this, MovePhase1.class);
                i.putExtra("plaatsLegers", plaatsLegers);
                startActivity(i);
                break;
            case R.id.buttonAddRandomCard:
                addRandomCard(player);
                break;
        }
    }
}
