package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreMove extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_move);

        Button buttonGame = (Button) findViewById(R.id.buttonGame);
        buttonGame.setOnClickListener(this);
        Button buttonMovePhase1 = (Button) findViewById(R.id.buttonMovePhase1);
        buttonMovePhase1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonGame:
                i = new Intent(this, Game.class);
                startActivity(i);
                break;
            case R.id.buttonMovePhase1:
                i = new Intent(this, MovePhase1.class);
                startActivity(i);
                break;
        }
    }

}
