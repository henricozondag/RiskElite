package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Game extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button buttonPreMove = (Button) findViewById(R.id.buttonPreMove);
        buttonPreMove.setOnClickListener(this);
        Button buttonEndGame = (Button) findViewById(R.id.buttonEndGame);
        buttonEndGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonPreMove:
                i = new Intent(this, PreMove.class);
                startActivity(i);
                break;
            case R.id.buttonEndGame:
                i = new Intent(this, EndGame.class);
                startActivity(i);
                break;
        }
    }
}
