package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button buttonHighScore = (Button) findViewById(R.id.buttonHighScore);
        buttonHighScore.setOnClickListener(this);
        Button buttonSettings = (Button) findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(this);
        Button buttonToGame = (Button) findViewById(R.id.buttonToGame);
        buttonToGame.setOnClickListener(this);
        Button buttonLoadGame = (Button) findViewById(R.id.buttonLoadGame);
        buttonLoadGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonHighScore:
                i = new Intent(this, HighScore.class);
                startActivity(i);
                break;
            case R.id.buttonSettings:
                i = new Intent(this, Settings.class);
                startActivity(i);
                break;
            case R.id.buttonToGame:
                i = new Intent(this, StartGame.class);
                startActivity(i);
                break;
            case R.id.buttonLoadGame:
                Intent intent = new Intent(Menu.this, LoadGame.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}
