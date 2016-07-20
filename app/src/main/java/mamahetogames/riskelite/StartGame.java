package mamahetogames.riskelite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class StartGame extends AppCompatActivity  implements View.OnClickListener {

    private final MyDBHandler db = new MyDBHandler(this);
    private EditText editTextGameName;
    private Spinner spinnerLegers, spinnerPlayers, spinnerWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        editTextGameName = (EditText) this.findViewById(R.id.editTextGameName);
        
        Button buttonGame = (Button) findViewById(R.id.buttonGame);
        buttonGame.setOnClickListener(this);

        // Vul deze spinner een aantal opties
        spinnerLegers = (Spinner) findViewById(R.id.spinnerLegers);
        List<String> list = new ArrayList<>();
        list.add("4");
        list.add("6");
        list.add("8");
        list.add("10");
        list.add("12");

        // Vul deze spinner een aantal opties
        spinnerPlayers = (Spinner) findViewById(R.id.spinnerPlayers);
        List<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");
        list2.add("4");
        list2.add("5");
        list2.add("6");

        // Vul deze spinner met al de beschikbare werelden
        spinnerWorld = (Spinner) findViewById(R.id.spinnerWorld);
        Cursor c = db.getWorlds();
        ArrayList<String> list3 = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            list3.add(c.getString(0));
        }
        c.close();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, list);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, list2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, list3);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinnerLegers.setAdapter(dataAdapter);
        spinnerPlayers.setAdapter(dataAdapter2);
        spinnerWorld.setAdapter(dataAdapter3);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonGame:
                i = new Intent(this, Game.class);

                //insert het aantal players dat geselecteerd is
                db.startGame(editTextGameName.getText().toString(),String.valueOf(spinnerLegers.getSelectedItem()),String.valueOf(spinnerPlayers.getSelectedItem()),String.valueOf(spinnerWorld.getSelectedItem()));
                Log.i("editTextGameName", editTextGameName.getText().toString());
                Log.i("spinnerLegers",String.valueOf(spinnerLegers.getSelectedItem()));
                Log.i("spinnerPlayers", String.valueOf(spinnerPlayers.getSelectedItem()));
                Log.i("spinnerWorld", String.valueOf(spinnerWorld.getSelectedItem()));

                startActivity(i);
                break;
        }
    }
}
