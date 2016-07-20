package mamahetogames.riskelite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MoveActionResult extends AppCompatActivity implements View.OnClickListener {

    private final MyDBHandler db = new MyDBHandler(this);
    public int gameID, dices;

    private SeekBar nrArmies = null;
    int maxSeedBar;
    int minSeedBar;
    int startSeedBar;
    int moveArmies;
    int value;
    public TextView textViewSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action_result);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        // getdata
        Bundle b = getIntent().getExtras();
        int totalLostA = b.getInt("totalLostA");
        int totalLostD = b.getInt("totalLostD");
        int winnaar_id = b.getInt("winnaar");
        dices =      b.getInt("dices");

        Log.i("dices", String.valueOf(dices));
        Log.i("winnaar", String.valueOf(winnaar_id));


        // winnaar naam ophalen aan de hand van id
        String winnaar = db.getPlayerName(winnaar_id,gameID);

        // alle runtime veldslagdata verwijderen
        db.resetCountries(gameID);

        // scherm vullen
        TextView textViewWinnaar = (TextView) this.findViewById(R.id.textViewWinnaar);
        TextView textViewLostD = (TextView) this.findViewById(R.id.textViewLostD);
        TextView textViewLostA = (TextView) this.findViewById(R.id.textViewLostA);
        textViewSeekBar = (TextView) this.findViewById(R.id.textViewSeekBar);
        textViewWinnaar.setText(winnaar);
        textViewLostA.setText(String.valueOf(totalLostA));
        textViewLostD.setText(String.valueOf(totalLostD));

        Button buttonMovePhase2 = (Button) findViewById(R.id.buttonBackMovePhase2);
        buttonMovePhase2.setOnClickListener(this);

        //seekbar

        nrArmies = (SeekBar) findViewById(R.id.seekBar);

        maxSeedBar = 10;
        minSeedBar = dices;
        startSeedBar = dices;
        value = startSeedBar;

        nrArmies.setMax(maxSeedBar);

        int calc = ((startSeedBar - minSeedBar) / (maxSeedBar - minSeedBar)) *100;

        moveArmies = (int) calc;
        nrArmies.setProgress(moveArmies);

        textViewSeekBar.setText(String.valueOf(value));

        nrArmies.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                value = ((maxSeedBar-minSeedBar) * (int) progress / 100) + minSeedBar;
                textViewSeekBar.setText(String.valueOf(value));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MoveActionResult.this,"seek bar progress:"+value,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonBackMovePhase2:
                //checken of speler meer dan 4 kaarten heeft, dan moet hij eerst kaarten wisselen namelijk
                if (db.countCards(Integer.parseInt(db.currentPlayer(gameID,"ID"))) > 4) {
                    i = new Intent(this, PlayerDetails.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    // Start andere scherm
                    i = new Intent(this, MovePhase2.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                break;
        }
    }
}
