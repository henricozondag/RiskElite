package mamahetogames.riskelite;

import android.content.Context;
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
    public int[][] cardType = new int[3][10];
    ImageView imageViewCardType1, imageViewCardType2, imageViewCardType3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        // Haal waarde op uit bundle die meegestuurd is bij opstarten scherm, dit is de speler waarvoor je de kaarten bekijkt.
        Bundle b = getIntent().getExtras();
        player = b.getInt("player");
        status = b.getString("status");
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

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

    public void laatKaartenZienNieuw () {

        // standaard beginnen met een leeg scherm
        for (int n = 0; n <= 9; n++) {
            checkBoxCard[n].setChecked(false);
            checkBoxCard[n].setVisibility(View.INVISIBLE);
            imageViewCard[n].setImageResource(0);
        }
       //showcard geeft aan welke van de 10 kaarten gevuld is
       int showCard = 0;
        //open database
       //SQLiteDatabase mydatabase = openOrCreateDatabase("riskElite3.db", MODE_PRIVATE, null);
        //MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        //dbHandler.playerCards(player, PACKAGE_NAME);
        // Ophalen van het type kaart en de hoeveelheid van dat type
       String PACKAGE_NAME = getApplicationContext().getPackageName();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Cursor playerCards = dbHandler.testKlas(player);
       //Cursor playerCards = mydatabase.rawQuery("Select type, number from cards where player = " + player, null);
       playerCards.moveToFirst();

        // for loop zo vaak als er kaarten zijn
        for (int n = 1; n <= playerCards.getCount(); n++) {

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
        //mydatabase.close();
    }

    public void laatKaartenZien () {

        // standaard beginnen met een leeg scherm
        for (int n = 0; n <= 9; n++) {
            checkBoxCard[n].setChecked(false);
            checkBoxCard[n].setVisibility(View.INVISIBLE);
            imageViewCard[n].setImageResource(0);
        }
        //showcard geeft aan welke van de 10 kaarten gevuld is
        int showCard = 0;
        //open database
        SQLiteDatabase mydatabase = openOrCreateDatabase("riskElite3", MODE_PRIVATE, null);
        // Ophalen van het type kaart en de hoeveelheid van dat type
        String PACKAGE_NAME = getApplicationContext().getPackageName();
        Cursor playerCards = mydatabase.rawQuery("Select type, number from cards where player = " + player, null);
        playerCards.moveToFirst();

        // for loop zo vaak als er kaarten zijn
        for (int n = 1; n <= playerCards.getCount(); n++) {

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
        //mydatabase.close();
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

        //SQLiteDatabase mydatabase = openOrCreateDatabase("riskElite3", MODE_PRIVATE, null);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        //achterhalen welke kaarten aangevinkt waren en daarvan de status op 0 zetten
        //hierdoor veranderd het plaatje in een kruis en verdwijnt de checkbox onder het plaatje

        dbHandler.removeCards(player, cardList);
        laatKaartenZien();
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
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.addRandomCard(player);
        laatKaartenZien();
    }

    public void addRandomCard2(int player) {
        SQLiteDatabase mydatabase = openOrCreateDatabase("riskElite2", MODE_PRIVATE, null);
        int type = ran.nextInt(3) + 1;

        // update het type kaart met +1
        mydatabase.execSQL("update cards set number = number + 1 where player = " + player + " and type = " + type);
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
