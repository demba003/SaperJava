package javalab.pk.saper.Controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javalab.pk.saper.Model.Bomba;
import javalab.pk.saper.Model.Plansza;
import javalab.pk.saper.Model.PustePole;
import javalab.pk.saper.R;
import javalab.pk.saper.View.PlanszaView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{
    Plansza plansza;
    PlanszaView planszaView;

    TextView czas;
    int count = 0;

    private Handler customHandler = new Handler();

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            count++;
            czas.setText(" Czas: " + String.valueOf(count));
                        customHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        czas = (TextView) findViewById(R.id.czas);

        plansza = Plansza.get(this, 8, 4, 2);
        planszaView = new PlanszaView(this, plansza, this, this);
        plansza.setPlanszaView(planszaView);
    }

    @Override
    public void onClick(View view) {
        if(count==0) customHandler.postDelayed(updateTimerThread, 0);
        int x = view.getId() / 10;
        int y = view.getId() % 10;

        if(plansza.getField(x,y) instanceof PustePole) plansza.floodFill(x, y);
        if(!plansza.getField(x,y).isOpened) plansza.open(x, y);

        if (plansza.getField(x,y) instanceof Bomba || plansza.getOpened() >= 30){
            Intent intentend = new Intent(getApplicationContext(), EndActivity.class);
            intentend.putExtra("wincond",plansza.getOpened());
            intentend.putExtra("count",count);
            startActivity(intentend);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onLongClick(View view) {
        if(count==0) customHandler.postDelayed(updateTimerThread, 0);
        int x = view.getId() / 10;
        int y = view.getId() % 10;
        planszaView.markAsBomb(x, y);
        return true;
    }
}
