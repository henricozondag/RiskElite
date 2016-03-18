package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MovePhase2 extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_phase2);

        Button buttonMovePhase3 = (Button) findViewById(R.id.buttonMovePhase3);
        buttonMovePhase3.setOnClickListener(this);
        Button buttonMoveAction = (Button) findViewById(R.id.buttonMoveAction);
        buttonMoveAction.setOnClickListener(this);
        Button buttonPlayerDetails = (Button) findViewById(R.id.buttonPlayerDetails);
        buttonPlayerDetails.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase3:
                i = new Intent(this, MovePhase3.class);
                startActivity(i);
                break;
            case R.id.buttonMoveAction:
                i = new Intent(this, MoveAction.class);
                startActivity(i);
                break;
            case R.id.buttonPlayerDetails:
                i = new Intent(this, PlayerDetails.class);
                startActivity(i);
                break;
        }
    }
}
