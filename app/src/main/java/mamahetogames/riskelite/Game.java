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

    TextView textViewGameId;
    int gameID,numberOfPlayers;
    MyDBHandler db = new MyDBHandler(this);
    EditText[] editTextSpeler = new EditText[6];
    TextView[] textViewSpeler = new TextView[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        textViewGameId = (TextView) this.findViewById(R.id.textViewGameId);
        textViewGameId.setText(String.valueOf(gameID));

        //invulschermen voor spelernamen maken

        // eerst bepalen hoeveel spelers er zijn en dan afhankelijk daarvan het aantal velden laten zien. (dus een array nog van maken dat is makkelijker)
        editTextSpeler[0] = (EditText) this.findViewById(R.id.editTextSpeler1);
        editTextSpeler[1] = (EditText) this.findViewById(R.id.editTextSpeler2);
        editTextSpeler[2] = (EditText) this.findViewById(R.id.editTextSpeler3);
        editTextSpeler[3] = (EditText) this.findViewById(R.id.editTextSpeler4);
        editTextSpeler[4] = (EditText) this.findViewById(R.id.editTextSpeler5);
        editTextSpeler[5] = (EditText) this.findViewById(R.id.editTextSpeler6);
        textViewSpeler[0] = (TextView) this.findViewById(R.id.textViewSpeler1);
        textViewSpeler[1] = (TextView) this.findViewById(R.id.textViewSpeler2);
        textViewSpeler[2] = (TextView) this.findViewById(R.id.textViewSpeler3);
        textViewSpeler[3] = (TextView) this.findViewById(R.id.textViewSpeler4);
        textViewSpeler[4] = (TextView) this.findViewById(R.id.textViewSpeler5);
        textViewSpeler[5] = (TextView) this.findViewById(R.id.textViewSpeler6);

        editTextSpeler[0].requestFocus();

        // Alleen spelers tonen die in het spel zitten
        numberOfPlayers = db.numberOfPlayers(gameID);

        for (int i=2; i < numberOfPlayers; i++) {
            editTextSpeler[i].setVisibility(View.VISIBLE);
            textViewSpeler[i].setVisibility(View.VISIBLE);
        }

        //button Start spel
        Button buttonPreMove = (Button) findViewById(R.id.buttonPreMove);
        buttonPreMove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonPreMove:
                i = new Intent(this, PreMove.class);

                //Opslaan namen van spelers
                for (int j=0; j < numberOfPlayers; j++) {
                    db.setPlayerName(gameID,j+1,editTextSpeler[j].getText().toString());
                }

                db.startPlayer(gameID);
                startActivity(i);
                break;
        }
    }
}
