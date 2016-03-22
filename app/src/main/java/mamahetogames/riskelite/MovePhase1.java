package mamahetogames.riskelite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MovePhase1 extends AppCompatActivity implements View.OnClickListener {

    private int aantalLegers;

    TextView legerBijTeZetten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_phase1);

        legerBijTeZetten = (TextView) this.findViewById(R.id.legersBijTeZetten);

        aantalLegers = 3;
        legerBijTeZetten.setText("" + aantalLegers);

        Button buttonMovePhase2 =       (Button) findViewById(R.id.buttonMovePhase2);
        Button buttonPutArmy =          (Button) findViewById(R.id.buttonPutArmy);
        buttonMovePhase2.setOnClickListener(this);
        buttonPutArmy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.buttonMovePhase2:
                i = new Intent(this, MovePhase2.class);
                startActivity(i);
                break;
            case R.id.buttonPutArmy:
                legerBijZetten();
                break;

        }
    }

    public void legerBijZetten() {
        if (aantalLegers >= 1) {
            aantalLegers = aantalLegers - 1;
            legerBijTeZetten.setText("" + aantalLegers);
        }
        else {
            Toast.makeText(getApplicationContext(), "Geen legers meer bij te zetten!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if ( motionEvent.getX() > 0) {
                    legerBijZetten();
                    Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());

                } else {
                    legerBijZetten();
                    Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());
                    }
                }
        return true;
        }

}
