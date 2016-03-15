package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;

public class MoveAction extends AppCompatActivity implements View.OnClickListener{

    int resultDice1, resultDice2, resultDice3, resultDice4, resultDice5, numberOfDice, numberOfDiceDefend;
    int topA1, topA2, topA3, topD1, topD2, lostA, lostD;
    ImageView imageDice1, imageDice2, imageDice3, imageDice4, imageDice5;
    Random ran = new Random();
    RadioButton radioButtonDice1, radioButtonDice2, radioButtonDice3, radioButtonDice4, radioButtonDice5;
    TextView textViewALost, textViewDLost;
    Button buttonGooiAttack, buttonMovePhase2, buttonGooiDefend, buttonCalcResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action);

        radioButtonDice1 = (RadioButton) findViewById(R.id.radioButtonDice1);
        radioButtonDice2 = (RadioButton) findViewById(R.id.radioButtonDice2);
        radioButtonDice3 = (RadioButton) findViewById(R.id.radioButtonDice3);
        radioButtonDice4 = (RadioButton) findViewById(R.id.radioButtonDice4);
        radioButtonDice5 = (RadioButton) findViewById(R.id.radioButtonDice5);
        textViewALost = (TextView) this.findViewById(R.id.textViewALost);
        textViewDLost = (TextView) this.findViewById(R.id.textViewDLost);

        // button om terug te gaan naar MovePhase2
        buttonMovePhase2 = (Button) findViewById(R.id.buttonMovePhase2);
        buttonMovePhase2.setOnClickListener(this);

        // button waarmee de aanvaller zijn dobbelstenen gaat gooien
        buttonGooiAttack = (Button) findViewById(R.id.buttonGooiAttack);
        buttonGooiAttack.setOnClickListener(this);

        // button waarmee de verdediger zijn dobbelstenen gaat gooien
        buttonGooiDefend = (Button) findViewById(R.id.buttonGooiDefend);
        buttonGooiDefend.setOnClickListener(this);

        // de dobbelstenen
        imageDice1 = (ImageView)findViewById(R.id.imageDice1);
        imageDice2 = (ImageView)findViewById(R.id.imageDice2);
        imageDice3 = (ImageView)findViewById(R.id.imageDice3);
        imageDice4 = (ImageView)findViewById(R.id.imageDice4);
        imageDice5 = (ImageView)findViewById(R.id.imageDice5);
    }

    public void rollDiceAttack(int numberOfDice) {
        resultDice1 = ran.nextInt(6)+1;

        switch(resultDice1){
            case 1: imageDice1.setImageResource(R.mipmap.dice_1); break;
            case 2: imageDice1.setImageResource(R.mipmap.dice_2); break;
            case 3: imageDice1.setImageResource(R.mipmap.dice_3); break;
            case 4: imageDice1.setImageResource(R.mipmap.dice_4); break;
            case 5: imageDice1.setImageResource(R.mipmap.dice_5); break;
            case 6: imageDice1.setImageResource(R.mipmap.dice_6); break;
        }

        resultDice2 = 0;
        resultDice3 = 0;
        if (numberOfDice > 1) {
            resultDice2 = ran.nextInt(6)+1;
            switch (resultDice2) {
                case 1: imageDice2.setImageResource(R.mipmap.dice_1); break;
                case 2: imageDice2.setImageResource(R.mipmap.dice_2); break;
                case 3: imageDice2.setImageResource(R.mipmap.dice_3); break;
                case 4: imageDice2.setImageResource(R.mipmap.dice_4); break;
                case 5: imageDice2.setImageResource(R.mipmap.dice_5); break;
                case 6: imageDice2.setImageResource(R.mipmap.dice_6); break;
            }
            resultDice3 = 0;
        }
        if (numberOfDice > 2) {
            resultDice3 = ran.nextInt(6)+1;
            switch (resultDice3) {
                case 1: imageDice3.setImageResource(R.mipmap.dice_1); break;
                case 2: imageDice3.setImageResource(R.mipmap.dice_2); break;
                case 3: imageDice3.setImageResource(R.mipmap.dice_3); break;
                case 4: imageDice3.setImageResource(R.mipmap.dice_4); break;
                case 5: imageDice3.setImageResource(R.mipmap.dice_5); break;
                case 6: imageDice3.setImageResource(R.mipmap.dice_6); break;
            }
        }

    }

    public void rollDiceDefend(int numberOfDiceDefend) {
        resultDice4 = ran.nextInt(6)+1;

        switch(resultDice4){
            case 1: imageDice4.setImageResource(R.mipmap.dice_1); break;
            case 2: imageDice4.setImageResource(R.mipmap.dice_2); break;
            case 3: imageDice4.setImageResource(R.mipmap.dice_3); break;
            case 4: imageDice4.setImageResource(R.mipmap.dice_4); break;
            case 5: imageDice4.setImageResource(R.mipmap.dice_5); break;
            case 6: imageDice4.setImageResource(R.mipmap.dice_6); break;
        }

        resultDice5 = 0;
        if (numberOfDiceDefend > 1) {
            resultDice5 = ran.nextInt(6)+1;
            switch (resultDice5) {
                case 1: imageDice5.setImageResource(R.mipmap.dice_1); break;
                case 2: imageDice5.setImageResource(R.mipmap.dice_2); break;
                case 3: imageDice5.setImageResource(R.mipmap.dice_3); break;
                case 4: imageDice5.setImageResource(R.mipmap.dice_4); break;
                case 5: imageDice5.setImageResource(R.mipmap.dice_5); break;
                case 6: imageDice5.setImageResource(R.mipmap.dice_6); break;
            }
        }

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
                lostD = lostD + 1;
            } else lostA = lostA + 1;
        }

        textViewALost.setText(String.valueOf(lostA));
        textViewDLost.setText(String.valueOf(lostD));
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

                if (radioButtonDice1.isChecked())
                    numberOfDice = 1;
                imageDice3.setImageResource(R.mipmap.black);
                imageDice2.setImageResource(R.mipmap.black);
                if (radioButtonDice2.isChecked())
                    numberOfDice = 2;
                imageDice3.setImageResource(R.mipmap.black);
                if (radioButtonDice3.isChecked())
                    numberOfDice = 3;
                rollDiceAttack(numberOfDice);
                //buttonGooiAttack.setVisibility(View.GONE);
                break;
            case R.id.buttonGooiDefend:

                if (radioButtonDice4.isChecked())
                    numberOfDiceDefend = 1;
                imageDice5.setImageResource(R.mipmap.black);
                if (radioButtonDice5.isChecked())
                    numberOfDiceDefend = 2;
                rollDiceDefend(numberOfDiceDefend);
                //buttonGooiDefend.setVisibility(View.GONE);
                calculateResult();
                break;
        }
    }
}
