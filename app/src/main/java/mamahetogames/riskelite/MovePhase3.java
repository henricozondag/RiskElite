package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MovePhase3 extends AppCompatActivity implements View.OnClickListener{

    MyDBHandler db = new MyDBHandler(this);
    int gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_phase3);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //status zetten van speler
        db.setPlayerStatus(gameID, "phase3");

        Button buttonPreMove = (Button) findViewById(R.id.buttonPreMove);
        buttonPreMove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonPreMove:
                i = new Intent(this, PreMove.class);

                //Volgende speler de beurt geven
                db.nextPlayer(db.getActiveGameID());

                startActivity(i);
                break;
        }
    }
}
