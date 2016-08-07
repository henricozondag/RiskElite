package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MovePhase3 extends AppCompatActivity implements View.OnClickListener{

    private final MyDBHandler db = new MyDBHandler(this);
    private int gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_phase3);

        //actieve game ophalen
        gameID = db.getActiveGameID();

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
                db.nextPlayer(gameID);
                db.setPlayerStatus(gameID, "premove");
                startActivity(i);
                break;
        }
    }
}
