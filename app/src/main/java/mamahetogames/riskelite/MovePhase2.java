package mamahetogames.riskelite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovePhase2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private int gameID;
    private int currentPlayerId;
    private int armiesAttacker;
    private int armiesDefender;
    private final MyDBHandler db = new MyDBHandler(this);
    private Spinner spinnerAttackCountry, spinnerDefendCountry;
    private TextView textViewAttackArmies;
    private TextView textViewDefendArmies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_phase2);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //current player_id ophalen
        currentPlayerId = Integer.parseInt(db.currentPlayer(gameID,"ID"));

        //status zetten van speler
        db.setPlayerStatus(gameID, "phase2");

        Button buttonMovePhase3 = (Button) findViewById(R.id.buttonMovePhase3);
        buttonMovePhase3.setOnClickListener(this);
        Button buttonMoveAction = (Button) findViewById(R.id.buttonMoveAction);
        buttonMoveAction.setOnClickListener(this);
        Button buttonPlayerDetails = (Button) findViewById(R.id.buttonPlayerDetails);
        buttonPlayerDetails.setOnClickListener(this);

        textViewAttackArmies = (TextView) this.findViewById(R.id.textViewAttackArmies);
        textViewDefendArmies = (TextView) this.findViewById(R.id.textViewDefendArmies);

        // spinners om 2 landen te selecteren waarmee je wil aanvallen
        // Vul deze spinner met al de beschikbare werelden
        spinnerAttackCountry = (Spinner) findViewById(R.id.spinnerAttackCountry);
        Cursor c = db.getCountries(gameID, currentPlayerId, "owner");
        ArrayList<String> list1 = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            list1.add(c.getString(0));
        }
        c.close();

        spinnerDefendCountry = (Spinner) findViewById(R.id.spinnerDefendCountry);
        Cursor d = db.getCountries(gameID, currentPlayerId, "nowner");
        ArrayList<String> list2 = new ArrayList<>();
        for(d.moveToFirst(); !d.isAfterLast(); d.moveToNext()) {
            list2.add(d.getString(0));
        }
        d.close();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,list1);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,list2);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAttackCountry.setAdapter(dataAdapter);
        spinnerDefendCountry.setAdapter(dataAdapter2);

        spinnerAttackCountry.setOnItemSelectedListener(this);
        spinnerDefendCountry.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parentView,View v,int position,long id) {

        switch (parentView.getId()) {
            case R.id.spinnerAttackCountry:
                String attack = spinnerAttackCountry.getItemAtPosition(position).toString();
                armiesAttacker = db.getCountryArmies(attack, gameID)-1;
                textViewAttackArmies.setText(String.valueOf(armiesAttacker));
                break;
            case R.id.spinnerDefendCountry:
                String defend = spinnerDefendCountry.getItemAtPosition(position).toString();
                armiesDefender = db.getCountryArmies(defend, gameID);
                textViewDefendArmies.setText(String.valueOf(armiesDefender));
                checkAttack(false);
                break;
            default:
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parentView){
        //zodat de spinners niet gaan flippen
    }

    private void checkAttack(Boolean start) {

        if  (db.isOwner(currentPlayerId,String.valueOf(spinnerAttackCountry.getSelectedItem()),gameID)) {
            // check of het verdedigende land NIET van de aanvaller is
            Log.i("isowner","true");
            if (!(db.isOwner(currentPlayerId,String.valueOf(spinnerDefendCountry.getSelectedItem()),gameID))) {
                //Check of de landen aan elkaar grenzen
                Log.i("isowner","false dus goed");
                if (db.isNeighbour(String.valueOf(spinnerAttackCountry.getSelectedItem()),String.valueOf(spinnerDefendCountry.getSelectedItem()),gameID)) {
                    Log.i("isneighbour","true");
                    if (start) {
                        db.initAttack(String.valueOf(spinnerAttackCountry.getSelectedItem()), String.valueOf(spinnerDefendCountry.getSelectedItem()), gameID);
                        Intent i = new Intent(this, MoveAction.class);
                        startActivity(i);
                    }
                }
                else
                    Log.i("Buren?","geen buren");
                Toast.makeText(getApplicationContext(),
                        "Landen grenzen niet aan elkaar!", Toast.LENGTH_SHORT).show();
            }
            else
                Log.i("defender","is van attacker");
        } else
            Log.i("aanvaller","is niet van aanvaller");
    }

        @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase3:
                i = new Intent(this, MovePhase3.class);
                startActivity(i);
                break;
            case R.id.buttonMoveAction:
                //Checken of aanval land wel van de actieve speler is
                checkAttack(true);
                break;
            case R.id.buttonPlayerDetails:
                i = new Intent(this, PlayerDetails.class);
                startActivity(i);
                break;
        }
    }
}
