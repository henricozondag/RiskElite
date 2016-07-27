package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MoveActionResult extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private final MyDBHandler db = new MyDBHandler(this);
    public int gameID, dices, currentPlayerId, attackArmies;
    String attackCountry, defendCountry, armies;
    private Spinner spinnerArmies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action_result);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //current player_id ophalen
        currentPlayerId = Integer.parseInt(db.currentPlayer(gameID,"ID"));

        //aantal overgebleven legers op het land ophalen
        attackCountry = db.getGameCountry("ATTACK", gameID);
        defendCountry = db.getGameCountry("DEFEND", gameID);
        attackArmies = db.getCountryArmies(attackCountry, gameID)-1;
        Log.i("attackarmies1", String.valueOf(attackArmies));

        // getdata
        Bundle b = getIntent().getExtras();
        int totalLostA  = b.getInt("totalLostA");
        int totalLostD  = b.getInt("totalLostD");
        int winnaar_id  = b.getInt("winnaar");
        dices           = b.getInt("dices");

        Log.i("dices", String.valueOf(dices));
        Log.i("winnaar", String.valueOf(winnaar_id));

        // winnaar naam ophalen aan de hand van id
        String winnaar = db.getPlayerName(gameID,winnaar_id);

        // alle runtime veldslagdata verwijderen
        db.resetCountries(gameID);

        // scherm vullen
        TextView textViewWinnaar = (TextView) this.findViewById(R.id.textViewWinnaar);
        TextView textViewLostD = (TextView) this.findViewById(R.id.textViewLostD);
        TextView textViewLostA = (TextView) this.findViewById(R.id.textViewLostA);
        textViewWinnaar.setText(winnaar);
        textViewLostA.setText(String.valueOf(totalLostA));
        textViewLostD.setText(String.valueOf(totalLostD));

        Button buttonMovePhase2 = (Button) findViewById(R.id.buttonBackMovePhase2);
        buttonMovePhase2.setOnClickListener(this);

        //<<Tijdelijk een knop om actieve speler te laten winnen
        Button buttonMakeWinner = (Button) findViewById(R.id.buttonTmpMakeWinner);
        buttonMakeWinner.setOnClickListener(this);
        //>>

        ArrayList<String> list1 = new ArrayList<>();
        spinnerArmies = (Spinner) findViewById(R.id.spinnerArmies);

        if (winnaar_id == currentPlayerId) {
            // Spinner voor het aantal door te schuiven legers
            for (int i = 1; i <= attackArmies; i++) {
                if (i >= dices) {
                    list1.add(String.valueOf(i));
                    Log.i("attackarmies2", String.valueOf(attackArmies));
                }
            }
        } else {
            // spinner vullen met 0
            list1.add(String.valueOf(0));
            spinnerArmies.setVisibility(View.INVISIBLE);
        }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                    (this, android.R.layout.simple_spinner_item, list1);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerArmies.setAdapter(dataAdapter);
            spinnerArmies.setOnItemSelectedListener(this);
        }

    public void onItemSelected(AdapterView<?> parentView,View v,int position,long id) {
        armies = spinnerArmies.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> parentView){
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonTmpMakeWinner:
                db.makeWinner(Integer.parseInt(db.currentPlayer(gameID,"ID")), gameID);
                break;
            case R.id.buttonBackMovePhase2:
                if (db.checkWinner(Integer.parseInt(db.currentPlayer(gameID,"ID")), gameID)) {
                    //als speler het spel gewonnen heeft dan spel beeindigen
                    i = new Intent(this, GameResult.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    db.setPlayerStatus(gameID, "finished");
                    startActivity(i);
                } else if (db.countCards(Integer.parseInt(db.currentPlayer(gameID,"ID"))) > 4) {
                    //als speler meer dan 4 kaarten heeft, dan moet hij eerst kaarten wisselen
                    i = new Intent(this, PlayerDetails.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    // alle runtime veldslagdata verwijderen
                    db.setCountryArmies(defendCountry,Integer.parseInt(armies),"PLUS",gameID);
                    db.setCountryArmies(attackCountry,Integer.parseInt(armies),"MIN",gameID);
                    db.resetCountries(gameID);
                    startActivity(i);
                } else {
                    // Start andere scherm
                    i = new Intent(this, MovePhase2.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    // alle runtime veldslagdata verwijderen
                    db.setCountryArmies(defendCountry,Integer.parseInt(armies),"PLUS",gameID);
                    db.setCountryArmies(attackCountry,Integer.parseInt(armies),"MIN",gameID);
                    db.resetCountries(gameID);
                    startActivity(i);
                }
                break;
        }
    }
}
