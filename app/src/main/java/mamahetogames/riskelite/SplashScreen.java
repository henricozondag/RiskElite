package mamahetogames.riskelite;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Wacht 2500ms en ga door naar menu
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i;
                i = new Intent(SplashScreen.this, Menu.class);
                startActivity(i);
            }
        }, 25);
    }
        }
