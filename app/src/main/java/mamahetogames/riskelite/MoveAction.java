package mamahetogames.riskelite;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

        import java.util.Random;

public class MoveAction extends AppCompatActivity implements View.OnClickListener{

    int myResult;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action);

        Button buttonMovePhase2 = (Button) findViewById(R.id.buttonMovePhase2);
        buttonMovePhase2.setOnClickListener(this);
        Button buttonGooiAttack = (Button) findViewById(R.id.buttonGooiAttack);
        buttonGooiAttack.setOnClickListener(this);
        imageView2 = (ImageView)findViewById(R.id.imageView2);;
    }

    public void rollDice() {
        myResult = new Random(System.currentTimeMillis()).nextInt(6)+1;
        switch(myResult){
            case 1:
                imageView2.setImageResource(R.mipmap.dice_1);
                break;

            case 2:
                imageView2.setImageResource(R.mipmap.dice_2);
                break;

            case 3:
                imageView2.setImageResource(R.mipmap.dice_3);
                break;

            case 4:
                imageView2.setImageResource(R.mipmap.dice_4);
                break;

            case 5:
                imageView2.setImageResource(R.mipmap.dice_5);
                break;

            case 6:
                imageView2.setImageResource(R.mipmap.dice_6);
                break;

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
            case R.id.buttonGooiAttack:
                rollDice();
                break;
        }
    }
}
