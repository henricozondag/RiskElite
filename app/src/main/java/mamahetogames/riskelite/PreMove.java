package mamahetogames.riskelite;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PreMove extends AppCompatActivity  implements View.OnClickListener {

    private TextView textViewCurrentPlayer;
    private int gameID, currentPlayerId;
    private int backButtonCount;
    private final MyDBHandler db = new MyDBHandler(this);
    String armyRed = "red_soldier";
    String armyBlue = "blue_soldier";
    String armyYellow = "yellow_soldier";
    String armyGreen = "green_soldier";
    private ImageView imageViewArmy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_move);

        //actieve game ophalen
        gameID = db.getActiveGameID();

        //setting zetten dat deze beurt nog geen card verkregen is
        db.setAttackCard(gameID, "N");

        //speler_name ophalen die aan de beurt is
        String player = db.currentPlayer(gameID,"name");
        currentPlayerId = Integer.parseInt(db.currentPlayer(gameID,"ID"));

        Button buttonMovePhase1 = (Button) findViewById(R.id.buttonMovePhase1);
        buttonMovePhase1.setOnClickListener(this);
        Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(this);
        Button buttonPas = (Button) findViewById(R.id.buttonPas);
        buttonPas.setOnClickListener(this);

        textViewCurrentPlayer = (TextView) this.findViewById(R.id.textViewCurrentPlayer);
        textViewCurrentPlayer.setText(player);

        imageViewArmy = (ImageView)findViewById(R.id.imageViewArmy);
        String PACKAGE_NAME = getApplicationContext().getPackageName();
        int imgId;
        imgId = getResources().getIdentifier(PACKAGE_NAME + ":mipmap/" + armyRed, null, null);
        imageViewArmy.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));

        // definieer de cursor uit de database handler en loop door de records heen
        String gamePlayer = db.currentPlayer(gameID, "gameplayer");
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
    }

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "Druk nog een keer op de back om terug te gaan naar het menu.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase1:
                //checken of speler meer dan 4 kaarten heeft, dan moet hij eerst kaarten wisselen namelijk
                if (db.countCards(currentPlayerId) > 4) {
                    i = new Intent(this, PlayerDetails.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    // Start andere scherm
                    i = new Intent(this, MovePhase1.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    db.setPlayerStatus(gameID, "phase1");
                    startActivity(i);
                }
                break;
            case R.id.buttonMenu:
                i = new Intent(this, Menu.class);
                // Start andere scherm
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            case R.id.buttonPas:
                i = new Intent(this, MovePhase1.class);
                // Start andere scherm
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                db.setPlayerStatus(gameID, "pas");
                db.updateArmiesToPlace(currentPlayerId,db.passArmies(currentPlayerId, gameID),"+");
                startActivity(i);
                break;
        }
    }
}
