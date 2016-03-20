package mamahetogames.riskelite;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MoveActionResult extends AppCompatActivity {

    String winnaar, LostD, LostA;
    TextView textViewWinnaar, textViewLostA, textViewLostD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action_result);
        loadSavedPreferences();
        textViewWinnaar = (TextView) this.findViewById(R.id.textViewWinnaar);
        textViewLostD = (TextView) this.findViewById(R.id.textViewLostD);
        textViewLostA = (TextView) this.findViewById(R.id.textViewLostA);
        textViewWinnaar.setText(winnaar);
        textViewLostA.setText(LostA);
        textViewLostD.setText(LostD);
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        winnaar = sharedPreferences.getString("Winnaar: ", "niemand");
        LostA = sharedPreferences.getString("Aanvaller verloren: ", "null");
        LostD = sharedPreferences.getString("Verdediger verloren: ", "null");
    }
}
