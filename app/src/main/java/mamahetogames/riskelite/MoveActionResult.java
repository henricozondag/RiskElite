package mamahetogames.riskelite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MoveActionResult extends AppCompatActivity {

    String winnaar;
    TextView textViewWinnaar, textViewLostA, textViewLostD;
    int totalLostA, totalLostD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action_result);
        Bundle b = getIntent().getExtras();
        totalLostA = b.getInt("totalLostA");
        totalLostD = b.getInt("totalLostD");
        winnaar = b.getString("winnaar");
        textViewWinnaar = (TextView) this.findViewById(R.id.textViewWinnaar);
        textViewLostD = (TextView) this.findViewById(R.id.textViewLostD);
        textViewLostA = (TextView) this.findViewById(R.id.textViewLostA);
        textViewWinnaar.setText(winnaar);
        textViewLostA.setText(Integer.toString(totalLostA));
        textViewLostD.setText(Integer.toString(totalLostD));
    }
}
