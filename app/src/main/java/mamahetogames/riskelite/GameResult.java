package mamahetogames.riskelite;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GameResult extends AppCompatActivity implements View.OnClickListener  {

    private final MyDBHandler db = new MyDBHandler(this);
    public int gameID;
    private ImageView imageViewArmy;
    String armyRed = "red_soldier";
    String armyBlue = "blue_soldier";
    String armyYellow = "yellow_soldier";
    String armyGreen = "green_soldier";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);


        //Wat willen we: 4 statistieken voor alle spelers Armies en Countries won/lost
        Log.i("gameResult","onCreate, woohoo");

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //spel beeindigen
        db.endGame(gameID);

        //Speler_name ophalen die aan de beurt is, en deze tonen op het scherm
        Cursor statistics = db.getStatistics(gameID);

        //Winnaar uit cursor halen om te zien of die het doet
        statistics.moveToFirst();
        TextView textViewWinnerName = (TextView) this.findViewById(R.id.textViewWinnerName);
        textViewWinnerName.setText(statistics.getString(1));

        //Plaatje bij winnaar tonen
        imageViewArmy = (ImageView)findViewById(R.id.imageViewArmy);
        String PACKAGE_NAME = getApplicationContext().getPackageName();
        int imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/" + armyRed, null, null);
        imageViewArmy.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
        String gamePlayer = statistics.getString(7);
        // bepaal de kleur voor deze speler
        switch (gamePlayer) {
            case "1":
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/" + armyRed, null, null);
                imageViewArmy.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;
            case "2":
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/" + armyBlue, null, null);
                imageViewArmy.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;
            case "3":
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/" + armyYellow, null, null);
                imageViewArmy.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;
            case "4":
                imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/" + armyGreen, null, null);
                imageViewArmy.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));
                break;
        }

        TextView textViewWinnerCountriesText = (TextView) this.findViewById(R.id.textViewWinnerCountries);
        textViewWinnerCountriesText.setText("Landen: "  + statistics.getString(5) + "gewonnen, " + statistics.getString(6) + " verloren");

        TextView textViewWinnerArmiesText = (TextView) this.findViewById(R.id.textViewWinnerArmies);
        textViewWinnerArmiesText.setText("Legers: "  + statistics.getString(3) + "gewonnen, " + statistics.getString(4) + " verloren");

        Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(this);

        //Losers informatie
        String loserText = "Verliezers: \n";
        for (int i = 1; i < statistics.getCount(); i++) {
            statistics.moveToNext();
            loserText = loserText + "Speler " + statistics.getString(1) + ":\n"
                                  + "   Landen: " + statistics.getString(5) + " gewonnen, " + statistics.getString(6) + " verloren\n"
                                  + "   Legers: " +  statistics.getString(3) + " gewonnen, " + statistics.getString(4) + " verloren\n\n";
        }

        TextView textViewLoserText = (TextView) this.findViewById(R.id.textViewLosers);
        textViewLoserText.setText(loserText);
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
