package mamahetogames.riskelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PreMove extends AppCompatActivity  implements View.OnClickListener {

    TextView textViewGameKey, textViewCurrentPlayer;
    int gameID;
    MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_move);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //speler_id ophalen die aan de beurt is
        int activePlayer = db.currentPlayer(gameID);

        //spelernaam ophalen van speler die aan de beurt is
        String player = db.nameCurrentPlayer(activePlayer);

        Button buttonMovePhase1 = (Button) findViewById(R.id.buttonMovePhase1);
        buttonMovePhase1.setOnClickListener(this);

        textViewGameKey = (TextView) this.findViewById(R.id.textViewGameKey);
        textViewGameKey.setText(String.valueOf(gameID));

        textViewCurrentPlayer = (TextView) this.findViewById(R.id.textViewCurrentPlayer);
        textViewCurrentPlayer.setText(player);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase1:
                i = new Intent(this, MovePhase1.class);
                // Start andere scherm
                startActivity(i);
                break;
        }
    }
}
