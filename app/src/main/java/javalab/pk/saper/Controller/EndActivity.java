package javalab.pk.saper.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javalab.pk.saper.R;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent myintent = getIntent();
        int count = myintent.getIntExtra("count",0);
        int wincond = myintent.getIntExtra("wincond",0);

        TextView czas2 = (TextView) findViewById(R.id.czas2);
        TextView win = (TextView) findViewById(R.id.win);
        Button start = (Button) findViewById(R.id.replay);
        Button exit = (Button) findViewById(R.id.exit1);

        czas2.setText("Twój czas wynosi: " + String.valueOf(count) + "s");
        if(wincond>=30){

            win.setText("Wygrałeś!");
        }
        else
        {
            win.setText("Przegrałeś!");
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finishAffinity();
                System.exit(0);
            }
        });


    }

}
