package mamahetogames.riskelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreMove extends AppCompatActivity  implements View.OnClickListener {

    TextView textViewGameKey, textViewCurrentPlayer;
    int gameID,backButtonCount;
    MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_move);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //status zetten van speler
        db.setPlayerStatus(gameID, "premove");

        //speler_name ophalen die aan de beurt is
        String player = db.currentPlayer(gameID,"name");

        Button buttonMovePhase1 = (Button) findViewById(R.id.buttonMovePhase1);
        buttonMovePhase1.setOnClickListener(this);
        Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(this);

        textViewGameKey = (TextView) this.findViewById(R.id.textViewGameKey);
        textViewGameKey.setText(String.valueOf(gameID));

        textViewCurrentPlayer = (TextView) this.findViewById(R.id.textViewCurrentPlayer);
        textViewCurrentPlayer.setText(player);
    }

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "Druk nog een keer op de back om terug te gaan naar het menu.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase1:
                i = new Intent(this, MovePhase1.class);
                // Start andere scherm
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            case R.id.buttonMenu:
                i = new Intent(this, Menu.class);
                // Start andere scherm
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }
    }
}
