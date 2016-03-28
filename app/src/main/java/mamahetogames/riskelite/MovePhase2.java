package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MovePhase2 extends AppCompatActivity implements View.OnClickListener{

    EditText editTextArmiesAttacker, editTextArmiesDefender;

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

        editTextArmiesAttacker = (EditText)findViewById(R.id.editTextArmiesAttacker);
        editTextArmiesDefender =(EditText)findViewById(R.id.editTextArmiesDefender);
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
                // Bundle toevoegen aan startActivity zodat de MovePhase1 ook weet hoeveel legers er te plaatsen zijn.
                Bundle b = new Bundle();
                b.putInt("armiesAttacker", Integer.parseInt(editTextArmiesAttacker.getText().toString()));
                b.putInt("armiesDefender", Integer.parseInt(editTextArmiesDefender.getText().toString()));
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.buttonPlayerDetails:
                i = new Intent(this, PlayerDetails.class);
                i.putExtra("player", 1);
                i.putExtra("status", "phase2");
                startActivity(i);
                break;
        }
    }
}
