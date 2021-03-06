package mamahetogames.riskelite;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class MoveAction extends AppCompatActivity implements View.OnClickListener {

    private int numberOfDice;
    private int numberOfDiceDefend;
    private int lostA;
    private int lostD;
    private int totalLostA;
    private int totalLostD;
    private int gameID;
    private int attackArmies;
    private int defendArmies;
    private int attackPlayer;
    private int defendPlayer;
    private int activePlayer;
    private String attackCountry;
    private String defendCountry;
    private final ArrayList<Integer> topA = new ArrayList<>();
    private final ArrayList<Integer> topD = new ArrayList<>();
    private final Random ran = new Random();
    private RadioGroup radioGroupAttack;
    private RadioGroup radioGroupDefend;
    private TextView textViewALost;
    private TextView textViewDLost;
    private TextView textViewAttArmies;
    private TextView textViewDefArmies;
    private Button buttonGooiAttack;
    private Button buttonMovePhase2;
    private Button buttonGooiDefend;
    private Button buttonAanvallen;
    private final ImageView[] imageDice = new ImageView[5];
    private final RadioButton[] radioButtonDice = new RadioButton[5];

    private final MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action);

        radioGroupAttack = (RadioGroup) findViewById(R.id.radioButtonGroupAttack);
        radioButtonDice[0] = (RadioButton) findViewById(R.id.radioButtonDice1);
        radioButtonDice[1] = (RadioButton) findViewById(R.id.radioButtonDice2);
        radioButtonDice[2] = (RadioButton) findViewById(R.id.radioButtonDice3);
        radioGroupDefend = (RadioGroup) findViewById(R.id.radioButtonGroupDefend);
        radioButtonDice[3] = (RadioButton) findViewById(R.id.radioButtonDice4);
        radioButtonDice[4] = (RadioButton) findViewById(R.id.radioButtonDice5);

        textViewALost = (TextView) this.findViewById(R.id.textViewALost);
        textViewDLost = (TextView) this.findViewById(R.id.textViewDLost);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //actieve speler ophalen
        activePlayer = Integer.parseInt(db.currentPlayer(gameID,"ID"));

        //status zetten van speler
        db.setPlayerStatus(gameID, "moveaction");

        //Naam van aanvaller, aantal armies van aanvaller, player_id van aanvaller
        attackCountry = db.getGameCountry("ATTACK", gameID);
        attackArmies = db.getCountryArmies(attackCountry, gameID)-1;
        attackPlayer = db.getCountryOwner(attackCountry, gameID);
        Log.i("attackArmies", Integer.toString(attackArmies));

        //player_id van verdediger ophalen
        defendCountry = db.getGameCountry("DEFEND", gameID);
        defendArmies = db.getCountryArmies(defendCountry, gameID);
        defendPlayer = db.getCountryOwner(defendCountry, gameID);
        Log.i("defendArmies", Integer.toString(defendArmies));

        // textviews die het aantal armies bevatten waarmee aangevallen en verdedigt wordt
        textViewAttArmies = (TextView) this.findViewById(R.id.textViewAttArmies);
        textViewDefArmies = (TextView) this.findViewById(R.id.textViewDefArmies);
        textViewAttArmies.setText(String.valueOf(attackArmies));
        textViewDefArmies.setText(String.valueOf(defendArmies));

        // button om terug te gaan naar MovePhase2
        buttonMovePhase2 = (Button) findViewById(R.id.buttonMovePhase2);
        buttonMovePhase2.setOnClickListener(this);

        // button waarmee de aanvaller zijn dobbelstenen gaat gooien
        buttonGooiAttack = (Button) findViewById(R.id.buttonGooiAttack);
        buttonGooiAttack.setOnClickListener(this);

        // button waarmee de verdediger zijn dobbelstenen gaat gooien
        buttonGooiDefend = (Button) findViewById(R.id.buttonGooiDefend);
        buttonGooiDefend.setOnClickListener(this);

        // button waarmee je nog een keer kan aanvallen
        buttonAanvallen = (Button) findViewById(R.id.buttonAanvallen);
        buttonAanvallen.setOnClickListener(this);

        // de dobbelstenen
        imageDice[0] = (ImageView)findViewById(R.id.imageDice1);
        imageDice[1] = (ImageView)findViewById(R.id.imageDice2);
        imageDice[2] = (ImageView)findViewById(R.id.imageDice3);
        imageDice[3] = (ImageView)findViewById(R.id.imageDice4);
        imageDice[4] = (ImageView)findViewById(R.id.imageDice5);

        if (db.attackerThrown(gameID)){
            int numberDiceThrown = getNumberDiceThrown(gameID);

            // dices vullen voor de vergelijking
            for(int dice=0; dice<numberDiceThrown; dice++) {
                topA.add(db.getDiceValue(gameID,(dice+1)));
                setDiceImage(dice,topA.get(dice));
            }
            //Scherm klaar maken voor verdediger
            setupDiceDefender(defendArmies);

        }
        else {
            // Scherm klaar maken voor aanvaller
            setupDiceAttacker(attackArmies);
        }
    }

    private int getNumberDiceThrown(int game_id) {
        int number;
        if (db.diceThrown(game_id, 3)) {
            number = 3;
        } else if (db.diceThrown(game_id, 2)) {
            number = 2;
        } else {
            number = 1;
        }
        return number;
    }

    private void setDiceImage(int dice, int resultDice) {
        String PACKAGE_NAME = getApplicationContext().getPackageName();
        String fnm = "dice_" + Integer.toString(resultDice);
        int imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/" + fnm, null, null);
        imageDice[dice].setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
    }

    private void setupDiceAttacker(int attackArmies) {
        switch (attackArmies) {
            case 1:
                radioButtonDice[0].setVisibility(View.VISIBLE);
                radioGroupAttack.check(radioButtonDice[0].getId());
                break;
            case 2:
                radioButtonDice[0].setVisibility(View.VISIBLE);
                radioButtonDice[1].setVisibility(View.VISIBLE);
                radioGroupAttack.check(radioButtonDice[1].getId());
                break;
            default:
                radioButtonDice[0].setVisibility(View.VISIBLE);
                radioButtonDice[1].setVisibility(View.VISIBLE);
                radioButtonDice[2].setVisibility(View.VISIBLE);
                radioGroupAttack.check(radioButtonDice[2].getId());
                break;
        }
    }

    private void rollDiceAttack(int numberOfDice) {
        topA.clear();

        for(int dice=0; dice<numberOfDice; dice++) {
            topA.add(ran.nextInt(6) + 1);
            setDiceImage(dice,topA.get(dice));
        }

        //order de dices
        Collections.sort(topA);
        Collections.reverse(topA);

        //duw dobbelstenen de database in
        for(int dice=0; dice<numberOfDice; dice++) {
            db.setDice("ATTACK", topA.get(dice), dice, gameID);
        }

        setupDiceDefender(defendArmies);
    }

    private void setupDiceDefender(int defendArmies) {
        //Wat dingen verdwijnen en verschijnen
        radioButtonDice[0].setVisibility(View.INVISIBLE);
        radioButtonDice[1].setVisibility(View.INVISIBLE);
        radioButtonDice[2].setVisibility(View.INVISIBLE);
        buttonGooiDefend.setVisibility(View.VISIBLE);
        buttonGooiAttack.setVisibility(View.INVISIBLE);
        // aantal dobbelstenen voor de verdediger aanpassen aan de legers die nog beschikbaar zijn
        switch (defendArmies) {
            case 1:
                radioButtonDice[3].setVisibility(View.VISIBLE);
                radioGroupDefend.check(radioButtonDice[3].getId());
                break;
            default:
                radioButtonDice[3].setVisibility(View.VISIBLE);
                radioButtonDice[4].setVisibility(View.VISIBLE);
                radioGroupDefend.check(radioButtonDice[4].getId());
                break;
        }
                buttonMovePhase2.setVisibility(View.INVISIBLE);
    }

    private void rollDiceDefend(int numberOfDiceDefend) {
        topD.clear();
        int diceValue = 0;
        for(int dice=3; dice<numberOfDiceDefend+3; dice++) {
            topD.add(ran.nextInt(6) + 1);
            setDiceImage(dice, topD.get(diceValue));
            diceValue++;
        }

        radioButtonDice[3].setVisibility(View.INVISIBLE);
        radioButtonDice[4].setVisibility(View.INVISIBLE);
        buttonGooiDefend.setVisibility(View.INVISIBLE);
        buttonAanvallen.setVisibility(View.VISIBLE);

        //order de dices
        Collections.sort(topD);
        Collections.reverse(topD);

        //duw dobbelstenen de database in
        for(int dice=0; dice<numberOfDiceDefend; dice++) {
            db.setDice("DEFEND", topD.get(dice), dice, gameID);
        }
        buttonMovePhase2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
    }

    private void calculateResult() {
        lostA = 0;
        lostD = 0;

        if (topA.get(0) > topD.get(0)) {
            lostD = 1;
        } else {
            lostA = 1;
        }
        if (numberOfDice > 1 && numberOfDiceDefend > 1) {
            if (topA.get(1) > topD.get(1)) {
                lostD++;
            } else {
                lostA++;
            }
        }
        updateArmies();
    }

    private void updateArmies() {
        // Tonen van het aantal verloren legers van deze aanval
        textViewALost.setText(String.valueOf(lostA));
        textViewDLost.setText(String.valueOf(lostD));

        // Bijhouden van de totalen verliezen van de verschillende landen deze kunnen we later tonen
        totalLostA = totalLostA + lostA;
        totalLostD = totalLostD + lostD;

        //Bijwerken van het aantal legers welke nog op de landen staan en bijwerken database totalen
        attackArmies = attackArmies - lostA;
        db.setCountryArmies(attackCountry,lostA,"MIN",gameID);
        db.updateArmyResult(attackPlayer,"LOST",lostA);
        db.updateArmyResult(defendPlayer,"WON",lostA);
        Log.i("lostA", Integer.toString(lostA));

        defendArmies = defendArmies - lostD;
        db.setCountryArmies(defendCountry,lostD,"MIN",gameID);
        db.updateArmyResult(attackPlayer,"WON",lostD);
        db.updateArmyResult(defendPlayer,"LOST",lostD);
        Log.i("lostD", Integer.toString(lostD));

        textViewAttArmies.setText(String.valueOf(attackArmies));
        textViewDefArmies.setText(String.valueOf(defendArmies));

        //Controleren of de aanvaller of verdediger de slag verloren heeft
        if (attackArmies == 0) {
            // open een nieuwe activity met een attacker lost animatie
//            db.updateCountryResult(attackPlayer,"LOST",1);
//            db.updateCountryResult(defendPlayer,"WON",1);
            Intent i = new Intent(this, MoveActionResult.class);
            i.putExtra("winnaar", defendPlayer);
            i.putExtra("totalLostA", totalLostA);
            i.putExtra("totalLostD", totalLostD);
            i.putExtra("dices", 0);

            //status zetten van speler
            db.setPlayerStatus(gameID, "phase2");
            startActivity(i);
        }
        else if (defendArmies == 0) {
            // open een nieuwe activity met een defender lost animatie
            db.updateCountryResult(attackPlayer,"WON",1);
            db.updateCountryResult(defendPlayer,"LOST",1);
            db.updateCountryOwner(defendCountry,activePlayer, gameID);
            // inbouwen dat dit maar 1 keer per beurt mag
            if (Objects.equals(db.checkAttackCard(gameID),"N")) {
                db.addRandomCard(attackPlayer);
                db.setAttackCard(gameID, "J");
            }
            Intent i = new Intent(this, MoveActionResult.class);
            i.putExtra("winnaar", attackPlayer);
            i.putExtra("totalLostA", totalLostA);
            i.putExtra("totalLostD", totalLostD);
            i.putExtra("dices", numberOfDice);

            //status zetten van speler
            db.setPlayerStatus(gameID, "phase2");
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase2:
                i = new Intent(this, MoveActionResult.class);
                i.putExtra("winnaar", 0);
                i.putExtra("totalLostA", totalLostA);
                i.putExtra("totalLostD", totalLostD);
                i.putExtra("dices", 0);

                //status zetten van speler
                db.setPlayerStatus(gameID, "phase2");
                startActivity(i);
                break;
            case R.id.buttonGooiAttack:
                if (radioButtonDice[0].isChecked())
                    numberOfDice = 1;
                imageDice[2].setImageResource(R.mipmap.black);
                imageDice[1].setImageResource(R.mipmap.black);
                if (radioButtonDice[1].isChecked())
                    numberOfDice = 2;
                imageDice[2].setImageResource(R.mipmap.black);
                if (radioButtonDice[2].isChecked())
                    numberOfDice = 3;
                rollDiceAttack(numberOfDice);
                break;
            case R.id.buttonGooiDefend:
                if (radioButtonDice[3].isChecked()) {
                    numberOfDiceDefend = 1;
                    imageDice[4].setImageResource(R.mipmap.black);
                }
                else {
                    numberOfDiceDefend = 2;
                }
                rollDiceDefend(numberOfDiceDefend);
                calculateResult();
                db.resetDice(gameID);
                break;
            case R.id.buttonAanvallen:
                //Aantal dobbelstenen voor de aanvaller aanpassen aan de legers die nog beschikbaar zijn
                setupDiceAttacker(attackArmies);
                buttonGooiAttack.setVisibility(View.VISIBLE);
                buttonAanvallen.setVisibility(View.INVISIBLE);
                imageDice[0].setImageResource(R.mipmap.black);
                imageDice[1].setImageResource(R.mipmap.black);
                imageDice[2].setImageResource(R.mipmap.black);
                imageDice[3].setImageResource(R.mipmap.black);
                imageDice[4].setImageResource(R.mipmap.black);
                break;
        }
    }
}