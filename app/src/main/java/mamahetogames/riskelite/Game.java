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

        //Per speler kaarten initialiseren
        MyDBHandler db = new MyDBHandler(this);
        db.initCards(2);
        db.newGame("TEST2");

        //
        Button buttonPreMove = (Button) findViewById(R.id.buttonPreMove);
        buttonPreMove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonPreMove:
                i = new Intent(this, PreMove.class);
                startActivity(i);
                break;
        }
    }
}
