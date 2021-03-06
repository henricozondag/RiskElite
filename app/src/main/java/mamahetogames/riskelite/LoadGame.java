package mamahetogames.riskelite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LoadGame extends AppCompatActivity implements View.OnClickListener {

    private final MyDBHandler db = new MyDBHandler(this);
    private SimpleCursorAdapter dataAdapter;
    private int backButtonCount = 0;
    private int gameID;

    //public ArrayList<String> gameList = new ArrayList<>();
    private final TextView[] textViewGame = new TextView[10];
    private final CheckBox[] checkBoxGame = new CheckBox[10];
    private Button buttonLoadGame;
    private TextView textViewLaadGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);

        buttonLoadGame = (Button) findViewById(R.id.buttonLoadGame);
        buttonLoadGame.setOnClickListener(this);

        Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(this);

        // de games
        textViewGame[0] = (TextView)findViewById(R.id.textViewGame1);
        checkBoxGame[0] = (CheckBox)findViewById(R.id.checkBoxGame1);
        textViewGame[1] = (TextView)findViewById(R.id.textViewGame2);
        checkBoxGame[1] = (CheckBox)findViewById(R.id.checkBoxGame2);
        textViewGame[2] = (TextView)findViewById(R.id.textViewGame3);
        checkBoxGame[2] = (CheckBox)findViewById(R.id.checkBoxGame3);
        textViewGame[3] = (TextView)findViewById(R.id.textViewGame4);
        checkBoxGame[3] = (CheckBox)findViewById(R.id.checkBoxGame4);
        textViewGame[4] = (TextView)findViewById(R.id.textViewGame5);
        checkBoxGame[4] = (CheckBox)findViewById(R.id.checkBoxGame5);
        textViewGame[5] = (TextView)findViewById(R.id.textViewGame6);
        checkBoxGame[5] = (CheckBox)findViewById(R.id.checkBoxGame6);
        textViewGame[6] = (TextView)findViewById(R.id.textViewGame7);
        checkBoxGame[6] = (CheckBox)findViewById(R.id.checkBoxGame7);
        textViewGame[7] = (TextView)findViewById(R.id.textViewGame8);
        checkBoxGame[7] = (CheckBox)findViewById(R.id.checkBoxGame8);
        textViewGame[8] = (TextView)findViewById(R.id.textViewGame9);
        checkBoxGame[8] = (CheckBox)findViewById(R.id.checkBoxGame9);
        textViewGame[9] = (TextView)findViewById(R.id.textViewGame10);
        checkBoxGame[9] = (CheckBox)findViewById(R.id.checkBoxGame10);

        textViewLaadGame = (TextView)findViewById(R.id.textViewLaadGame);
        //showGames();
        displayListView();
    }

    private void displayListView() {

        Cursor cursor = db.fetchAllGames();

        // The desired columns to be bound
        String[] columns = new String[]{
                MyDBHandler.COLUMN_ID,
                MyDBHandler.COLUMN_NAME,
                MyDBHandler.COLUMN_STATUS
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.key,
                R.id.name,
                R.id.status,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.game_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Vul de cursus met de geselecteerde rij
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Query de naam en toon deze
                String gameNaam =
                        cursor.getString(cursor.getColumnIndexOrThrow("name"));

                // Query het game_id
                gameID =
                        cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                buttonLoadGame.setVisibility(View.VISIBLE);
                textViewLaadGame.setText(gameNaam);

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Druk nog een keer op de back button om het spel af te sluiten. De voortgang is opgeslagen.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonLoadGame:
                db.loadGame(gameID);
                String status = db.getPlayerStatus(gameID);
                switch (status) {
                    case "premove":
                        i = new Intent(this, PreMove.class);
                        break;
                    case "phase1":
                        i = new Intent(this, MapScreen.class);
                        break;
                    case "phase2":
                        i = new Intent(this, MovePhase2.class);
                        break;
                    case "moveaction":
                        i = new Intent(this, MoveAction.class);
                        break;
                    case "pas":
                        i = new Intent(this, MapScreen.class);
                        break;
                    case "game":
                        i = new Intent(this, Game.class);
                        break;
                    default:
                        i = new Intent(this, Menu.class);
                        break;
                }
                Toast.makeText(getApplicationContext(),
                        "Speler '" + db.currentPlayer(gameID, "name") + "' is aan de beurt!", Toast.LENGTH_SHORT).show();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            case R.id.buttonMenu:
                i = new Intent(this, Menu.class);
                startActivity(i);
                break;
        }
    }
}
