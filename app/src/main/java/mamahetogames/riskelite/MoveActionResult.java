package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoveActionResult extends AppCompatActivity implements View.OnClickListener{

    String winnaar;
    TextView textViewWinnaar, textViewLostA, textViewLostD;
    int totalLostA, totalLostD;
    Button buttonMovePhase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action_result);

        // getdata
        Bundle b = getIntent().getExtras();
        totalLostA = b.getInt("totalLostA");
        totalLostD = b.getInt("totalLostD");
        winnaar = b.getString("winnaar");

        // scherm vullen
        textViewWinnaar = (TextView) this.findViewById(R.id.textViewWinnaar);
        textViewLostD = (TextView) this.findViewById(R.id.textViewLostD);
        textViewLostA = (TextView) this.findViewById(R.id.textViewLostA);
        textViewWinnaar.setText(winnaar);
        textViewLostA.setText(Integer.toString(totalLostA));
        textViewLostD.setText(Integer.toString(totalLostD));

        buttonMovePhase2 = (Button) findViewById(R.id.buttonBackMovePhase2);
        buttonMovePhase2.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonBackMovePhase2:
                i = new Intent(this, MovePhase2.class);
                startActivity(i);
                break;
        }
    }
}
