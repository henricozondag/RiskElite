package mamahetogames.riskelite;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        db.initWorlds();
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
