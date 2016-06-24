package mamahetogames.riskelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game extends AppCompatActivity  implements View.OnClickListener {

    TextView textViewGameId;
    int gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //actieve game ophalen
        loadSavedPreferences();

        //Per speler kaarten initialiseren
        MyDBHandler db = new MyDBHandler(this);
        db.initCards(2);
        Button buttonPreMove = (Button) findViewById(R.id.buttonPreMove);
        buttonPreMove.setOnClickListener(this);

        textViewGameId = (TextView) this.findViewById(R.id.textViewGameId);
        textViewGameId.setText(String.valueOf(gameID));
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gameID = sharedPreferences.getInt("gameID", 0);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonPreMove:
                i = new Intent(this, PreMove.class);
                startActivity(i);
                break;
        }
    }
}
