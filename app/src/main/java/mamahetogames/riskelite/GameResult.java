package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GameResult extends AppCompatActivity implements View.OnClickListener  {

    private final MyDBHandler db = new MyDBHandler(this);
    public int gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        Log.i("gameResult","onCreate, woohoo");

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //spel beeindigen
        db.endGame(gameID);

        Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(this);

        //Wat willen we verder hier doen? Toon gegevens van winnaar (incl juiste kleur) en daaronder gegevens van andere spelers oid?
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMenu:
                //Ga terug naar menu
                i = new Intent(this, Menu.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }
    }
}
