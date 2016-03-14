package mamahetogames.riskelite;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.Toast;
        import java.util.Random;

public class MoveAction extends AppCompatActivity implements View.OnClickListener{

    int resultDice1, resultDice2, resultDice3, numberOfDice;
    ImageView imageDice1, imageDice2, imageDice3;
    Random ran = new Random();
    Spinner dropdown;
    public String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_action);

        // alle zooi om de spinner op het scherm te tonen
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"3", "2", "1"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                Object item = arg0.getItemAtPosition(arg2);
                if (item!=null) {
                    Toast.makeText(MoveAction.this, item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MoveAction.this, "Selected",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        String text = dropdown.getSelectedItem().toString();

        // Code om de string uit de spinner om te zetten naar een int
        numberOfDice = Integer.parseInt(text.replaceAll("[\\D]",""));

        // button om terug te gaan naar buttonMovePhase
        Button buttonMovePhase2 = (Button) findViewById(R.id.buttonMovePhase2);
        buttonMovePhase2.setOnClickListener(this);

        // button waarmee de aanvaller zijn dobbelstenen gaat gooien
        Button buttonGooiAttack = (Button) findViewById(R.id.buttonGooiAttack);
        buttonGooiAttack.setOnClickListener(this);
        imageDice1 = (ImageView)findViewById(R.id.imageDice1);
        imageDice2 = (ImageView)findViewById(R.id.imageDice2);
        imageDice3 = (ImageView)findViewById(R.id.imageDice3);
    }

    public void rollDice(int numberOfDice) {
        resultDice1 = ran.nextInt(7);

        switch(resultDice1){
            case 1: imageDice1.setImageResource(R.mipmap.dice_1); break;
            case 2: imageDice1.setImageResource(R.mipmap.dice_2); break;
            case 3: imageDice1.setImageResource(R.mipmap.dice_3); break;
            case 4: imageDice1.setImageResource(R.mipmap.dice_4); break;
            case 5: imageDice1.setImageResource(R.mipmap.dice_5); break;
            case 6: imageDice1.setImageResource(R.mipmap.dice_6); break;
        }
        if (numberOfDice > 1) {
            resultDice2 = ran.nextInt(7);
            switch (resultDice2) {
                case 1: imageDice2.setImageResource(R.mipmap.dice_1); break;
                case 2: imageDice2.setImageResource(R.mipmap.dice_2); break;
                case 3: imageDice2.setImageResource(R.mipmap.dice_3); break;
                case 4: imageDice2.setImageResource(R.mipmap.dice_4); break;
                case 5: imageDice2.setImageResource(R.mipmap.dice_5); break;
                case 6: imageDice2.setImageResource(R.mipmap.dice_6); break;
            }
        }
            if (numberOfDice > 2) {
                resultDice3 = ran.nextInt(7);
                switch (resultDice3) {
                    case 1: imageDice3.setImageResource(R.mipmap.dice_1); break;
                    case 2: imageDice3.setImageResource(R.mipmap.dice_2); break;
                    case 3: imageDice3.setImageResource(R.mipmap.dice_3); break;
                    case 4: imageDice3.setImageResource(R.mipmap.dice_4); break;
                    case 5: imageDice3.setImageResource(R.mipmap.dice_5); break;
                    case 6: imageDice3.setImageResource(R.mipmap.dice_6); break;
                }
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
                rollDice(numberOfDice);
                break;
        }
    }
}
