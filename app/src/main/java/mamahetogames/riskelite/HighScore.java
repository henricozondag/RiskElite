package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighScore extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMenu:
                i = new Intent(this, Menu.class);
                startActivity(i);
                break;
        }
    }
}
