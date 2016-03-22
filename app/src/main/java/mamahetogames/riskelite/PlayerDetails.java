package mamahetogames.riskelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class PlayerDetails extends AppCompatActivity implements View.OnClickListener{

    ImageView imageViewCard1, imageViewCard2, imageViewCard3, imageViewCard4, imageViewCard5;
    ImageView imageViewCard6, imageViewCard7, imageViewCard8,imageViewCard9,imageViewCard10;
    CheckBox checkBoxCard1, checkBoxCard2,checkBoxCard3, checkBoxCard4, checkBoxCard5;
    CheckBox checkBoxCard6, checkBoxCard7, checkBoxCard8, checkBoxCard9, checkBoxCard10;
    int typeCard1, typeCard2, typeCard3, typeCard4, typeCard5, typeCard6, typeCard7, typeCard8, typeCard9, typeCard10;
    int player = 1;
    // welke status heeft de speler? (phase1/2 of 3) ivm het wel of niet mogen ruilen van de kaarten
    //String status = "phase1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        Button buttonMovePhase2 = (Button) findViewById(R.id.buttonMovePhase2);
        buttonMovePhase2.setOnClickListener(this);
        Button buttonRuilKaarten = (Button) findViewById(R.id.buttonRuilKaarten);
        buttonRuilKaarten.setOnClickListener(this);

        // de kaarten
        imageViewCard1 = (ImageView)findViewById(R.id.imageViewCard1);
        imageViewCard2 = (ImageView)findViewById(R.id.imageViewCard2);
        imageViewCard3 = (ImageView)findViewById(R.id.imageViewCard3);
        imageViewCard4 = (ImageView)findViewById(R.id.imageViewCard4);
        imageViewCard5 = (ImageView)findViewById(R.id.imageViewCard5);
        imageViewCard6 = (ImageView)findViewById(R.id.imageViewCard6);
        imageViewCard7 = (ImageView)findViewById(R.id.imageViewCard7);
        imageViewCard8 = (ImageView)findViewById(R.id.imageViewCard8);
        imageViewCard9 = (ImageView)findViewById(R.id.imageViewCard9);
        imageViewCard10 = (ImageView)findViewById(R.id.imageViewCard10);

        // haal op welke kaarten er allemaal actief zijn voor de huidige speler
        loadSavedPreferences();

        typeCard1 = 1;
        typeCard2 = 2;
        typeCard3 = 3;

        laatKaartenZien(player);
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
                imageViewCard1.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;
            case 2:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard2, null, null);
                imageViewCard2.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;
            case 3:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard3, null, null);
                imageViewCard3.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;
            case 4:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard3, null, null);
                imageViewCard4.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;
            default:
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + typeCard3, null, null);
                imageViewCard5.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;

        }
    }

    public void ruilKaarten() {

        // 1e set kaarten die ingeleverd wordt is 4 punten waard. 2e set 6 etc...

    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        typeCard1 = sharedPreferences.getInt("card1", 0);
        typeCard2 = sharedPreferences.getInt("card2", 0);
        typeCard3 = sharedPreferences.getInt("card3", 0);
        typeCard4 = sharedPreferences.getInt("card4", 0);
        typeCard5 = sharedPreferences.getInt("card5", 0);
        typeCard6 = sharedPreferences.getInt("card6", 0);
        typeCard7 = sharedPreferences.getInt("card7", 0);
        typeCard8 = sharedPreferences.getInt("card8", 0);
        typeCard9 = sharedPreferences.getInt("card9", 0);
        typeCard10 = sharedPreferences.getInt("card10", 0);
    }

    private void savePreferences(String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase2:
                i = new Intent(this, MovePhase2.class);
                startActivity(i);
                break;
        }
    }
}
