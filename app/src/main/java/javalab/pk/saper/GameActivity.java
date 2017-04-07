package javalab.pk.saper;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Vector;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    Vector<Vector<ImageButton>> buttons;
    Vector<Vector<Pole>> board;

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

        buttons = new Vector<>();
        board = new Vector<>();
        Random rnd = new Random();

        for (int i=0; i<6; i++){
            board.add(new Vector<Pole>());
            for (int j=0; j<6; j++) {
                board.get(i).add(new OdkrytePole());
            }
        }

        for (int i=0; i<6; i++) {
            int x = rnd.nextInt(6);
            int y = rnd.nextInt(6);
            if(board.get(x).get(y) instanceof Bomba)  {
                i--;
            }
            board.get(x).set(y, (new Bomba()));
        }


        LinearLayout layout = (LinearLayout) findViewById(R.id.game_layout);
        customHandler.postDelayed(updateTimerThread, 0);
        Iterator itx = board.iterator();

        int x=0, y=0;
        while(itx.hasNext()) {
            LinearLayout row = new LinearLayout(this);
            buttons.add(new Vector<ImageButton>());
            for (int j = 0; j < 6; j++) {
                buttons.get(x).add(new ImageButton(this));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.weight = 1;

                buttons.get(x).get(j).setLayoutParams(p);
                buttons.get(x).get(j).setBackground(null);
                buttons.get(x).get(j).setImageResource(R.mipmap.squareblank);

                buttons.get(x).get(j).setId(10*x + j);
                buttons.get(x).get(j).setOnClickListener(this);
                row.addView(buttons.get(x).get(j));
            }
            itx.next();
            x++;
            layout.addView(row);
        }
    }

    @Override
    public void onClick(View view) {
        if(count==0)customHandler.postDelayed(updateTimerThread, 0);
        int x = view.getId() / 10;
        int y = view.getId() % 10;
        buttons.get(x).get(y).setImageResource(board.get(x).get(y).action());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
