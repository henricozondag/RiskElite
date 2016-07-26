package mamahetogames.riskelite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Objects;

public class MovePhase1 extends AppCompatActivity implements View.OnClickListener {

    private int aantalLegers;
    private TextView legerBijTeZetten;
    private final MyDBHandler db = new MyDBHandler(this);
    private int gameID;
    private String playerStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_phase1);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //speler_id ophalen die aan de beurt is
        int activePlayer = Integer.parseInt(db.currentPlayer(gameID,"ID"));
        playerStatus = db.getPlayerStatus(gameID);

        // aantal te plaatsen legers ophalen
        aantalLegers = db.armyToPlace(activePlayer);

        legerBijTeZetten = (TextView) this.findViewById(R.id.legersBijTeZetten);
        legerBijTeZetten.setText(Integer.toString(aantalLegers));

        Button buttonMovePhase2 =       (Button) findViewById(R.id.buttonMovePhase2);
        Button buttonPutArmy =          (Button) findViewById(R.id.buttonPutArmy);
        buttonMovePhase2.setOnClickListener(this);
        buttonPutArmy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase2:
                if (Objects.equals(playerStatus, "phase1")) {
                    i = new Intent(this, MovePhase2.class);
                    //status zetten van speler
                    db.setPlayerStatus(gameID, "phase2");
                } else {
                    i = new Intent(this, MovePhase3.class);
                    //status zetten van speler
                    db.setPlayerStatus(gameID, "phase3");
                }
                startActivity(i);
                break;
            case R.id.buttonPutArmy:
                i = new Intent(this, MapScreen.class);
                startActivity(i);
                break;

        }
    }

    private void legerBijZetten() {
        if (aantalLegers >= 1) {
            aantalLegers = aantalLegers - 1;
            legerBijTeZetten.setText("" + aantalLegers);
        }
        else {
            Toast.makeText(getApplicationContext(), "Geen legers meer bij te zetten!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if ( motionEvent.getX() > 0) {
                    legerBijZetten();
                    Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());

                }   else if (motionEvent.getY() > 0) {
                        legerBijZetten();
                        Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());
                    }
                    else if (motionEvent.getY() > 0) {
                        legerBijZetten();
                        Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());
                    }
                    else if (motionEvent.getY() > 0) {
                        legerBijZetten();
                        Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());
                    }
                }
        return true;
        }

}
