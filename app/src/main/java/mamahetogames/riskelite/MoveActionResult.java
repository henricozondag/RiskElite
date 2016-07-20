package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoveActionResult extends AppCompatActivity implements View.OnClickListener{

    private final MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action_result);

        //actieve game ophalen
        int gameID = db.getActiveGameID();

        // getdata
        Bundle b = getIntent().getExtras();
        int totalLostA = b.getInt("totalLostA");
        int totalLostD = b.getInt("totalLostD");
        int winnaar_id = b.getInt("winnaar");

        // winnaar naam ophalen aan de hand van id
        String winnaar = db.getPlayerName(winnaar_id,gameID);

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
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonBackMovePhase2:
                i = new Intent(this, MovePhase2.class);
                startActivity(i);
                break;
        }
    }
}
