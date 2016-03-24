package mamahetogames.riskelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

public class MoveAction extends AppCompatActivity implements View.OnClickListener {

    int resultDice1, resultDice2, resultDice3, resultDice4, resultDice5, numberOfDice, numberOfDiceDefend;
    int topA1, topA2, topA3, topD1, topD2, lostA, lostD, totalLostA, totalLostD;
    Random ran = new Random();
    RadioGroup radioGroupAttack, radioGroupDefend;
    TextView textViewALost, textViewDLost, textViewAttArmies, textViewDefArmies;
    Button buttonGooiAttack, buttonMovePhase2, buttonGooiDefend, buttonAanvallen;
    ImageView[] imageDice = new ImageView[5];
    RadioButton[] radioButtonDice = new RadioButton[5];

    // de onderstaande ints moeten later gevuld worden vanuit het vorige scherm.
    // het betreft de aantallen waarmee aangevallen/verdedigd gaat worden
    int armiesAttacker = 5, armiesDefender = 3;

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

        // textviews die het aantal armies bevatten waarmee aangevallen en verdedigt wordt
        textViewAttArmies = (TextView) this.findViewById(R.id.textViewAttArmies);
        textViewDefArmies = (TextView) this.findViewById(R.id.textViewDefArmies);
        textViewAttArmies.setText(String.valueOf(armiesAttacker));
        textViewDefArmies.setText(String.valueOf(armiesDefender));

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

        // verstoppen van de defender button en dices
        radioButtonDice[3].setVisibility(View.INVISIBLE);
        radioButtonDice[4].setVisibility(View.INVISIBLE);
        buttonGooiDefend.setVisibility(View.INVISIBLE);
        buttonAanvallen.setVisibility(View.INVISIBLE);
    }

    public class rollDice {
        private int resultDice;
        public String PACKAGE_NAME = getApplicationContext().getPackageName();
        // setter
        void setDice(int dice) {
            resultDice = ran.nextInt(6) + 1;
            String fnm = "dice_" + Integer.toString(resultDice);
            int imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/" + fnm, null, null);
            imageDice[dice].setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
         }
        // getter
        int getDice() {
            return resultDice;
        }
    }

    public void rollDiceAttack(int numberOfDice) {
        rollDice rd = new rollDice();
        rd.setDice(0);
        resultDice1 = rd.getDice();
        resultDice2 = 0;
        resultDice3 = 0;
        if (numberOfDice > 1) {
            rd.setDice(1);
            resultDice2 = rd.getDice();
            resultDice3 = 0;
        }
        if (numberOfDice > 2) {
            rd.setDice(2);
            resultDice3 = rd.getDice();
        }
        radioButtonDice[0].setVisibility(View.INVISIBLE);
        radioButtonDice[1].setVisibility(View.INVISIBLE);
        radioButtonDice[2].setVisibility(View.INVISIBLE);
        buttonGooiDefend.setVisibility(View.VISIBLE);
        buttonGooiAttack.setVisibility(View.INVISIBLE);

        // aantal dobbelstenen voor de verdediger aanpassen aan de legers die nog beschikbaar zijn
        switch (armiesDefender) {
            case 1:
                radioButtonDice[3].setVisibility(View.VISIBLE);
                radioGroupDefend.check(radioButtonDice[3].getId());
                break;
            default:
                radioButtonDice[3].setVisibility(View.VISIBLE);
                radioButtonDice[4].setVisibility(View.VISIBLE);
                break;
        }
    }

    public void rollDiceDefend(int numberOfDiceDefend) {
        rollDice rd = new rollDice();
        rd.setDice(3);
        resultDice4 = rd.getDice();
        resultDice5 = 0;
        if (numberOfDiceDefend > 1) {
            rd.setDice(4);
            resultDice5 = rd.getDice();
        }
        radioButtonDice[3].setVisibility(View.INVISIBLE);
        radioButtonDice[4].setVisibility(View.INVISIBLE);
        buttonGooiDefend.setVisibility(View.INVISIBLE);
        buttonAanvallen.setVisibility(View.VISIBLE);
    }

    public void updateArmies() {
        // Tonen van het aantal verloren legers van deze aanval
        textViewALost.setText(String.valueOf(lostA));
        textViewDLost.setText(String.valueOf(lostD));

        // Bijhouden van de totalen verliezen van de verschillende landen deze kunnen we later tonen
        totalLostA = totalLostA + lostA;
        totalLostD = totalLostD + lostD;

        //Bijwerken van het aantal legers welke nog op de landen staan
        armiesAttacker = armiesAttacker - lostA;
        armiesDefender = armiesDefender - lostD;
        textViewAttArmies.setText(String.valueOf(armiesAttacker));
        textViewDefArmies.setText(String.valueOf(armiesDefender));

        //Controleren of de aanvaller of verdediger de slag verloren heeft
        if (armiesAttacker == 0) {
        // open een nieuwe activity met een attacker lost animatie
            Intent i = new Intent(this, MoveActionResult.class);
            i.putExtra("winnaar", "Verdediger");
            i.putExtra("totalLostA", totalLostA);
            i.putExtra("totalLostD", totalLostD);
            startActivity(i);
        }
        else if (armiesDefender == 0) {
        // open een nieuwe activity met een defender lost animatie
            // en met de statistieken van de veldslag
            Intent i = new Intent(this, MoveActionResult.class);
            i.putExtra("winnaar", "Aanvaller");
            i.putExtra("totalLostA", totalLostA);
            i.putExtra("totalLostD", totalLostD);
            startActivity(i);
        }
    };

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void orderResult() {
        if (resultDice1 > resultDice2 && resultDice1 > resultDice3) {
            topA1 = resultDice1;
            if (resultDice2 > resultDice3) {
                topA2 = resultDice2;
                topA3 = resultDice3;
            } else {
                topA2 = resultDice3;
                topA3 = resultDice2;
            }
        }
        else if (resultDice2 > resultDice1 && resultDice2 > resultDice3) {
            topA1 = resultDice2;
            if (resultDice1 > resultDice3) {
                topA2 = resultDice1;
                topA3 = resultDice3;
            } else {
                topA2 = resultDice3;
                topA3 = resultDice1;
            }
        }
        else if (resultDice1 > resultDice2) {
            topA1 = resultDice3;
            topA2 = resultDice1;
            topA3 = resultDice2;
        } else {
            topA1 = resultDice3;
            topA2 = resultDice2;
            topA3 = resultDice1;
        }
        if (resultDice4 > resultDice5) {
            topD1 = resultDice4;
            topD2 = resultDice5;
        } else {
            topD1 = resultDice5;
            topD2 = resultDice4;
        }
    }

    public void calculateResult() {
        orderResult();
        lostA = 0;
        lostD = 0;
        if (topA1 > topD1) {
            lostD = 1;
        }
        else {
            lostA = 1;
        }
        if (resultDice5 != 0 && resultDice2 != 0) {
            if (topA2 > topD2) {
                lostD++;
            } else lostA++;
        }
        updateArmies();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase2:
                i = new Intent(this, MovePhase2.class);
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
                if (radioButtonDice[3].isChecked())
                    numberOfDiceDefend = 1;
                imageDice[4].setImageResource(R.mipmap.black);
                if (radioButtonDice[4].isChecked())
                    numberOfDiceDefend = 2;
                rollDiceDefend(numberOfDiceDefend);
                calculateResult();
                break;
            case R.id.buttonAanvallen:
                //Aantal dobbelstenen voor de aanvaller aanpassen aan de legers die nog beschikbaar zijn
                switch (armiesAttacker) {
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