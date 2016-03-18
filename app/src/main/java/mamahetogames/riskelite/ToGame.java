package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToGame extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_game);


        Button buttonSetupGame = (Button) findViewById(R.id.buttonSetupGame);
        buttonSetupGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonSetupGame:
                i = new Intent(this, SetupGame.class);
                startActivity(i);
                break;
        }
    }
}
