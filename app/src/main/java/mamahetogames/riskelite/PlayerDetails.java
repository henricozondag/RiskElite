package mamahetogames.riskelite;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.Objects;

public class PlayerDetails extends AppCompatActivity implements View.OnClickListener{

    private final ArrayList<Integer> cardList = new ArrayList<>();
    private int plaatsLegers;
    private int gameID;
    private int currentPlayerId;
    private TextView textViewAantalLegers;
    private TextView textViewPlaatsenLegers;
    private Button buttonPlaatsenLegers;
    private Button buttonMovePhase2;
    private Button buttonRuilKaarten;
    private final ImageView[] imageViewCard = new ImageView[10];
    private final CheckBox[] checkBoxCard = new CheckBox[10];
    private final int[] cardType = new int[10];

    private final MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        gameID = db.getActiveGameID();
        currentPlayerId = Integer.parseInt(db.currentPlayer(gameID,"ID"));
        //player = Integer.parseInt(db.currentPlayer(gameID,"gameplayer"));
        // welke status heeft de speler? (phase1/2 of 3) ivm het wel of niet mogen ruilen van de kaarten dit moet nog wel geimplementeerd worden (knop wel niet tonen)
        //status = db.currentPlayer(gameID, "status");

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
        showCards();

        // check of er niet al een maximum van 10 kaarten is bereikt
        if (db.countCards(currentPlayerId) > 4) {
            buttonMovePhase2.setVisibility(View.INVISIBLE);
        }
    }

    //bitmap geneuzel
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
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

    private void showCards() {

        // standaard beginnen met een leeg scherm
        for (int n = 0; n <= 9; n++) {
            checkBoxCard[n].setChecked(false);
            checkBoxCard[n].setVisibility(View.INVISIBLE);
            imageViewCard[n].setImageResource(0);
        }
        //showcard geeft aan welke van de 10 kaarten gevuld is
        int showCard = 0;

        // Ophalen van het type kaart en de hoeveelheid van dat type
        String PACKAGE_NAME = getApplicationContext().getPackageName();

        Cursor playerCards = db.getCards(currentPlayerId);
        playerCards.moveToFirst();

        // for loop zo vaak als er kaarten zijn
        for (int n = 1; n <= playerCards.getCount(); n++) {

            //per type loop totdat alle kaarten er staan
            for (int cardNr = 1; cardNr <= playerCards.getInt(1); cardNr++) {

                //type van de kaart ophalen
                cardType[showCard] = playerCards.getInt(0);
                int imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/card" + cardType[showCard], null, null);
                imageViewCard[showCard].setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgId, 100, 100));
                checkBoxCard[showCard].setVisibility(View.VISIBLE);
                showCard++;
            }
            playerCards.moveToNext();
        }
    }

    private void convertCards() {

        int counter = 0;
        cardList.clear();
        for(int card=0; card<=9; card++) {
            // tellen hoeveel er zijn gecheckt
            if (checkBoxCard[card].isChecked()) {
                counter++;
                cardList.add(cardType[card]);
            }
        }
        //Bekijken welk soort setje is ingeleverd: (drie dezelfde? drie verschillende? fout?)
        if (counter == 3) {
            if (cardList.get(0).equals(cardList.get(1)) && cardList.get(0).equals(cardList.get(2))) {
                Toast.makeText(PlayerDetails.this, "Heel goed, je hebt drie dezelfde aangevinkt!",  Toast.LENGTH_LONG).show();
                addArmies();
                removeCards();
            }
            //else if (cardList.get(0) != cardList.get(1) && cardList.get(0) != cardList.get(2) && cardList.get(1) != cardList.get(2)) {
            else if (!Objects.equals(cardList.get(0), cardList.get(1)) && !Objects.equals(cardList.get(0), cardList.get(2)) && !Objects.equals(cardList.get(1), cardList.get(2))) {
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

    private void addArmies() {

        // geef het aantal legers wat geplaatst mag worden door aan variabele
        int armyCard = Integer.parseInt((db.getParameter("armyCard",gameID)));
        Log.i("armycardophalen", (db.getParameter("armyCard",gameID)));
        db.updateArmiesToPlace(currentPlayerId,armyCard,"+");
        plaatsLegers = plaatsLegers + armyCard;

        // schermdingen
        textViewAantalLegers.setVisibility(View.VISIBLE);
        textViewAantalLegers.setText(Integer.toString(plaatsLegers));
        textViewPlaatsenLegers.setVisibility(View.VISIBLE);
        buttonPlaatsenLegers.setVisibility(View.VISIBLE);
        buttonMovePhase2.setVisibility(View.INVISIBLE);

        armyCard = armyCard + 2;
        db.setParameter("armyCard", armyCard, gameID,"update");
    }

    private void removeCards() {

        MyDBHandler db = new MyDBHandler(this);

        db.removeCards(currentPlayerId, cardList);
        showCards();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase2:
                if (Objects.equals(db.getPlayerStatus(gameID),"pas")) {
                    i = new Intent(this, MovePhase1.class);
                } else {
                    i = new Intent(this, MovePhase2.class);
                    //status zetten van speler
                    db.setPlayerStatus(gameID, "phase2");
                }
                startActivity(i);
                break;
            case R.id.buttonRuilKaarten:
                convertCards();
                if (db.countCards(currentPlayerId) < 5) {
                    buttonMovePhase2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.buttonPlaatsenLegers:
                i = new Intent(this, MovePhase1.class);
                db.setPlayerStatus(gameID, "phase1");
                startActivity(i);
                break;
        }
    }
}
