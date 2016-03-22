package mamahetogames.riskelite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MovePhase1 extends AppCompatActivity implements View.OnClickListener {

    Canvas canvas;
    RiskMap RiskMap;
    Context context;
    int armieCard;
    private int aantalLegers;
    TextView legerBijTeZetten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_phase1);

        legerBijTeZetten = (TextView) this.findViewById(R.id.legersBijTeZetten);

        //RiskMap = new RiskMap(this);
        //setContentView(RiskMap);
        //Bitmap armyIcon;
        //armyIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.toy_soldier_clip_art_small);
        //canvas.drawBitmap(armyIcon, 0, 0, null);

        Bundle b = getIntent().getExtras();
        aantalLegers = b.getInt("plaatsLegers");
        legerBijTeZetten.setText(Integer.toString(aantalLegers));

        Button buttonMovePhase2 =       (Button) findViewById(R.id.buttonMovePhase2);
        Button buttonPutArmy =          (Button) findViewById(R.id.buttonPutArmy);
        buttonMovePhase2.setOnClickListener(this);
        buttonPutArmy.setOnClickListener(this);

    }

    class RiskMap extends SurfaceView {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        //Paint paint;

        public RiskMap (Context context) {
            super(context);
            ourHolder = getHolder();
            //paint = new Paint();

        }
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

                }   else if (motionEvent.getY() > 0) {
                        legerBijZetten();
                        Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());
                    }
                    else if (motionEvent.getY() > 0) {
                        legerBijZetten();
                        Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());
                    }
                    else if (motionEvent.getY() > 0) {
                        legerBijZetten();
                        Log.i("klik locatie", "" + motionEvent.getX()+ "  " +  motionEvent.getY());
                    }
                }
        return true;
        }

}
