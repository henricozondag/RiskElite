package mamahetogames.riskelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class PlayerDetails extends AppCompatActivity implements View.OnClickListener{

    public ArrayList<Integer> cardList = new ArrayList<>();
    int typeCard1, typeCard2, typeCard3, typeCard4, typeCard5, typeCard6, typeCard7, typeCard8, typeCard9, typeCard10;
    int player = 1, armieCard, plaatsLegers;
    // welke status heeft de speler? (phase1/2 of 3) ivm het wel of niet mogen ruilen van de kaarten
    String status = "phase1";
    TextView textViewAantalLegers, textViewPlaatsenLegers;
    Button buttonPlaatsenLegers, buttonMovePhase2, buttonRuilKaarten;
    ImageView[] imageViewCard = new ImageView[10];
    CheckBox[] checkBoxCard = new CheckBox[10];
    int[] typeCard = new int[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        buttonMovePhase2 = (Button) findViewById(R.id.buttonMovePhase2);
        buttonMovePhase2.setOnClickListener(this);
        buttonRuilKaarten = (Button) findViewById(R.id.buttonRuilKaarten);
        buttonRuilKaarten.setOnClickListener(this);
        buttonPlaatsenLegers = (Button) findViewById(R.id.buttonPlaatsenLegers);
        buttonPlaatsenLegers.setOnClickListener(this);

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
        laatKaartenZien(player);
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

    public void laatKaartenZien (int player) {
        for(int card=0; card<=9; card++) {
            kaartZichtbaar(card);
        }
    }

    public void kaartZichtbaar (int card) {

        String PACKAGE_NAME = getApplicationContext().getPackageName();
        int imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard[card], null, null);
        imageViewCard[card].setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
        if (typeCard[card] > 0)
            checkBoxCard[card].setVisibility(View.VISIBLE);
        else {
            checkBoxCard[card].setVisibility(View.INVISIBLE);
            checkBoxCard[card].setChecked(false);
        }
    }

    public void ruilKaarten() {

        int counter = 0;
        cardList.clear();
        for(int card=0; card<=9; card++) {
            // tellen hoeveel er zijn gecheckt
            if (checkBoxCard[card].isChecked()) {
                counter++;
                cardList.add(typeCard[card]);
            }
        }
        //Bekijken welk soort setje is ingeleverd: (drie dezelfde? drie verschillende? fout?)
        if (counter == 3) {
            if (cardList.get(0) == cardList.get(1) && cardList.get(0) == cardList.get(2)) {
                Toast.makeText(PlayerDetails.this, "Heel goed, je hebt drie dezelfde aangevinkt!",  Toast.LENGTH_LONG).show();
                addArmies();
                removeCards();
            }
            else if (cardList.get(0) != cardList.get(1) && cardList.get(0) != cardList.get(2) && cardList.get(1) != cardList.get(2))
            {
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

        // schermdingen
        textViewAantalLegers.setVisibility(View.VISIBLE);
        textViewAantalLegers.setText(Integer.toString(armieCard));
        textViewPlaatsenLegers.setVisibility(View.VISIBLE);
        buttonPlaatsenLegers.setVisibility(View.VISIBLE);
        buttonMovePhase2.setVisibility(View.INVISIBLE);

        plaatsLegers = armieCard;
        armieCard = armieCard + 2;
        savePreferences("armieCard", armieCard);
    }

    public void removeCards() {
        //achterhalen welke kaarten aangevinkt waren en daarvan de status op 0 zetten
        //hierdoor veranderd het plaatje in een kruis en verdwijnt de checkbox onder het plaatje
        for(int card=0; card<=9; card++) {
            if (checkBoxCard[card].isChecked()) {
                typeCard[card] = 0;
                savePreferences("typeCard"+card, typeCard[card]);
            }
        }
        //Log.i("typeCard1", Integer.toString(typeCard[0]));
        laatKaartenZien(player);
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        for(int card=0; card<=9; card++) {
            typeCard[card] = sharedPreferences.getInt("card"+card, 1);
        }
        armieCard = sharedPreferences.getInt("armieCard", 4);
    }

    private void savePreferences(String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
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
        }
    }
}
