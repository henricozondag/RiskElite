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

    TextView textViewGameKey;
    int gameID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_move);

        //actieve game ophalen
        loadSavedPreferences();

        Button buttonMovePhase1 = (Button) findViewById(R.id.buttonMovePhase1);
        buttonMovePhase1.setOnClickListener(this);

        textViewGameKey = (TextView) this.findViewById(R.id.textViewGameKey);
        textViewGameKey.setText(String.valueOf(gameID));
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gameID = sharedPreferences.getInt("gameID", 0);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase1:
                i = new Intent(this, MovePhase1.class);
                // Bundle toevoegen aan startActivity zodat de MovePhase1 ook weet hoeveel legers er te plaatsen zijn.
                Bundle b = new Bundle();
                b.putInt("plaatsLegers", 3);
                i.putExtras(b);
                // Start andere scherm
                startActivity(i);
                break;
        }
    }

}
