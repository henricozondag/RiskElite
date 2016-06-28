package mamahetogames.riskelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Game extends AppCompatActivity  implements View.OnClickListener {

    TextView textViewGameId, textViewStartspeler;
    Button   buttonPreMove, buttonStartSpeler;
    EditText editTextSpeler1, editTextSpeler2, editTextSpeler3, editTextSpeler4, editTextSpeler5, editTextSpeler6;
    int gameID;
    MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //Per speler kaarten initialiseren
        db.initCards(2);

        textViewGameId = (TextView) this.findViewById(R.id.textViewGameId);
        textViewGameId.setText(String.valueOf(gameID));

        //invulschermen voor spelernamen maken

        // eerst bepalen hoeveel spelers er zijn en dan afhankelijk daarvan het aantal velden laten zien. (dus een array nog van maken dat is makkelijker)

        editTextSpeler1 = (EditText) this.findViewById(R.id.editTextSpeler1);
        editTextSpeler2 = (EditText) this.findViewById(R.id.editTextSpeler2);
        editTextSpeler3 = (EditText) this.findViewById(R.id.editTextSpeler3);
        editTextSpeler4 = (EditText) this.findViewById(R.id.editTextSpeler4);
        editTextSpeler5 = (EditText) this.findViewById(R.id.editTextSpeler5);
        editTextSpeler6 = (EditText) this.findViewById(R.id.editTextSpeler6);

        //buttons
        Button buttonPreMove = (Button) findViewById(R.id.buttonPreMove);
        buttonPreMove.setOnClickListener(this);
        buttonPreMove.setVisibility(View.INVISIBLE);

        Button buttonStartSpeler = (Button) findViewById(R.id.buttonStartSpeler);
        buttonStartSpeler.setOnClickListener(this);
        buttonStartSpeler.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonPreMove:
                i = new Intent(this, PreMove.class);
                startActivity(i);
                break;
            case R.id.buttonStartSpeler:
                //startspeler bepalen
                db.startPlayer(gameID);
                //speler_id ophalen die aan de beurt is
                int activePlayer = db.currentPlayer(gameID);
                //spelernaam ophalen van speler die aan de beurt is
                String player = db.nameCurrentPlayer(activePlayer);
                textViewStartspeler.setText((player));
                buttonPreMove.setVisibility(View.VISIBLE);
                buttonStartSpeler.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
