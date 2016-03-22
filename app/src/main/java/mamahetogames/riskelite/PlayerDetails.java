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

    ImageView imageViewCard1, imageViewCard2, imageViewCard3, imageViewCard4, imageViewCard5;
    ImageView imageViewCard6, imageViewCard7, imageViewCard8,imageViewCard9,imageViewCard10;
    CheckBox checkBoxCard1, checkBoxCard2,checkBoxCard3, checkBoxCard4, checkBoxCard5;
    CheckBox checkBoxCard6, checkBoxCard7, checkBoxCard8, checkBoxCard9, checkBoxCard10;
    public ArrayList<Integer> cardList = new ArrayList<>();
    int typeCard1, typeCard2, typeCard3, typeCard4, typeCard5, typeCard6, typeCard7, typeCard8, typeCard9, typeCard10;
    int player = 1, armieCard, plaatsLegers;
    // welke status heeft de speler? (phase1/2 of 3) ivm het wel of niet mogen ruilen van de kaarten
    String status = "phase1";
    TextView textViewAantalLegers, textViewPlaatsenLegers;
    Button buttonPlaatsenLegers, buttonMovePhase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        buttonMovePhase2 = (Button) findViewById(R.id.buttonMovePhase2);
        buttonMovePhase2.setOnClickListener(this);
        Button buttonRuilKaarten = (Button) findViewById(R.id.buttonRuilKaarten);
        buttonRuilKaarten.setOnClickListener(this);
        buttonPlaatsenLegers = (Button) findViewById(R.id.buttonPlaatsenLegers);
        buttonPlaatsenLegers.setOnClickListener(this);


        // de kaarten
        imageViewCard1 = (ImageView)findViewById(R.id.imageViewCard1);
        checkBoxCard1 = (CheckBox)findViewById(R.id.checkBoxCard1);
        imageViewCard2 = (ImageView)findViewById(R.id.imageViewCard2);
        checkBoxCard2 = (CheckBox)findViewById(R.id.checkBoxCard2);
        imageViewCard3 = (ImageView)findViewById(R.id.imageViewCard3);
        checkBoxCard3 = (CheckBox)findViewById(R.id.checkBoxCard3);
        imageViewCard4 = (ImageView)findViewById(R.id.imageViewCard4);
        checkBoxCard4 = (CheckBox)findViewById(R.id.checkBoxCard4);
        imageViewCard5 = (ImageView)findViewById(R.id.imageViewCard5);
        checkBoxCard5 = (CheckBox)findViewById(R.id.checkBoxCard5);
        imageViewCard6 = (ImageView)findViewById(R.id.imageViewCard6);
        checkBoxCard6 = (CheckBox)findViewById(R.id.checkBoxCard6);
        imageViewCard7 = (ImageView)findViewById(R.id.imageViewCard7);
        checkBoxCard7 = (CheckBox)findViewById(R.id.checkBoxCard7);
        imageViewCard8 = (ImageView)findViewById(R.id.imageViewCard8);
        checkBoxCard8 = (CheckBox)findViewById(R.id.checkBoxCard8);
        imageViewCard9 = (ImageView)findViewById(R.id.imageViewCard9);
        checkBoxCard9 = (CheckBox)findViewById(R.id.checkBoxCard9);
        imageViewCard10 = (ImageView)findViewById(R.id.imageViewCard10);
        checkBoxCard10 = (CheckBox)findViewById(R.id.checkBoxCard10);

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
        for(int l=1; l<=10; l++) {
            kaartZichtbaar(l);
        }
    }

    public void kaartZichtbaar (int card) {

        String PACKAGE_NAME = getApplicationContext().getPackageName();
        switch (card) {
            case 1:
                int imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard1, null, null);
                imageViewCard1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard1 > 0)
                    checkBoxCard1.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard1.setVisibility(View.INVISIBLE);
                    checkBoxCard1.setChecked(false);
                }
                break;
            case 2:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard2, null, null);
                imageViewCard2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard2 > 0)
                    checkBoxCard2.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard2.setVisibility(View.INVISIBLE);
                    checkBoxCard2.setChecked(false);
                }
                break;
            case 3:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard3, null, null);
                imageViewCard3.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard3 > 0)
                    checkBoxCard3.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard3.setVisibility(View.INVISIBLE);
                    checkBoxCard3.setChecked(false);
                }
                break;
            case 4:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard4, null, null);
                imageViewCard4.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard4 > 0)
                    checkBoxCard4.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard4.setVisibility(View.INVISIBLE);
                    checkBoxCard4.setChecked(false);
                }
                break;
            case 5:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard5, null, null);
                imageViewCard5.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard5 > 0)
                    checkBoxCard5.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard5.setVisibility(View.INVISIBLE);
                    checkBoxCard5.setChecked(false);
                }
                break;
            case 6:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard6, null, null);
                imageViewCard6.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard6 > 0)
                    checkBoxCard6.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard6.setVisibility(View.INVISIBLE);
                    checkBoxCard6.setChecked(false);
                }
                break;
            case 7:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard7, null, null);
                imageViewCard7.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard7 > 0)
                    checkBoxCard7.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard7.setVisibility(View.INVISIBLE);
                    checkBoxCard7.setChecked(false);
                }
                break;
            case 8:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard8, null, null);
                imageViewCard8.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard8 > 0)
                    checkBoxCard8.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard8.setVisibility(View.INVISIBLE);
                    checkBoxCard8.setChecked(false);
                }
                break;
            case 9:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard9, null, null);
                imageViewCard9.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard9 > 0)
                    checkBoxCard9.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard9.setVisibility(View.INVISIBLE);
                    checkBoxCard9.setChecked(false);
                }
                break;
            case 10:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard10, null, null);
                imageViewCard10.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                if (typeCard10 > 0)
                    checkBoxCard10.setVisibility(View.VISIBLE);
                else {
                    checkBoxCard10.setVisibility(View.INVISIBLE);
                    checkBoxCard10.setChecked(false);
                }
                break;
        }
    }

    public void ruilKaarten() {

        int counter = 0;
        cardList.clear();
        // tellen hoeveel er zijn gecheckt
        if (checkBoxCard1.isChecked()) { counter++; cardList.add(typeCard1); }
        if (checkBoxCard2.isChecked()) { counter++; cardList.add(typeCard2); }
        if (checkBoxCard3.isChecked()) { counter++; cardList.add(typeCard3); }
        if (checkBoxCard4.isChecked()) { counter++; cardList.add(typeCard4); }
        if (checkBoxCard5.isChecked()) { counter++; cardList.add(typeCard5); }
        if (checkBoxCard6.isChecked()) { counter++; cardList.add(typeCard6); }
        if (checkBoxCard7.isChecked()) { counter++; cardList.add(typeCard7); }
        if (checkBoxCard8.isChecked()) { counter++; cardList.add(typeCard8); }
        if (checkBoxCard9.isChecked()) { counter++; cardList.add(typeCard9); }
        if (checkBoxCard10.isChecked()) { counter++; cardList.add(typeCard10); }

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
        if (checkBoxCard1.isChecked()) { typeCard1 = 0; savePreferences("typeCard1", typeCard1);}
        if (checkBoxCard2.isChecked()) { typeCard2 = 0; savePreferences("typeCard2", typeCard2);}
        if (checkBoxCard3.isChecked()) { typeCard3 = 0; savePreferences("typeCard3", typeCard3);}
        if (checkBoxCard4.isChecked()) { typeCard4 = 0; savePreferences("typeCard4", typeCard4);}
        if (checkBoxCard5.isChecked()) { typeCard5 = 0; savePreferences("typeCard5", typeCard5);}
        if (checkBoxCard6.isChecked()) { typeCard6 = 0; savePreferences("typeCard6", typeCard6);}
        if (checkBoxCard7.isChecked()) { typeCard7 = 0; savePreferences("typeCard7", typeCard7);}
        if (checkBoxCard8.isChecked()) { typeCard8 = 0; savePreferences("typeCard8", typeCard8);}
        if (checkBoxCard9.isChecked()) { typeCard9 = 0; savePreferences("typeCard9", typeCard9);}
        if (checkBoxCard10.isChecked()) { typeCard10 = 0; savePreferences("typeCard10", typeCard10);}

        //Log.i("typeCard1", Integer.toString(typeCard1));
        laatKaartenZien(player);
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        typeCard1 = sharedPreferences.getInt("card1", 1);
        typeCard2 = sharedPreferences.getInt("card2", 2);
        typeCard3 = sharedPreferences.getInt("card3", 3);
        typeCard4 = sharedPreferences.getInt("card4", 2);
        typeCard5 = sharedPreferences.getInt("card5", 1);
        typeCard6 = sharedPreferences.getInt("card6", 0);
        typeCard7 = sharedPreferences.getInt("card7", 0);
        typeCard8 = sharedPreferences.getInt("card8", 0);
        typeCard9 = sharedPreferences.getInt("card9", 0);
        typeCard10 = sharedPreferences.getInt("card10", 0);
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
