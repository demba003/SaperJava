package javalab.pk.saper;

import android.content.Intent;
//import android.graphics.Point;
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
//import java.util.LinkedList;
import java.util.Vector;
import java.util.Random;
//import java.util.Queue;

public class GameActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

    Vector<Vector<ImageButton>> buttons;
    Vector<Vector<Pole>> board;

    TextView czas;
    int count = 0;
    int wincond = 0; //Ilość pól pozostałych do wygranej


    private Handler customHandler = new Handler();

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            count++;
            czas.setText(" Czas: " + String.valueOf(count));
                        customHandler.postDelayed(this, 1000);

        }
    };


    protected void floodFill(int x, int y) {
        /* ZA POMOCA KOLEJKI, NA RAZIE NIE DZIALA :(
        Queue<Point> floodQ = new LinkedList<Point>();
        floodQ.add(new Point(x, y));
        while(!floodQ.isEmpty()){
            Point p = floodQ.poll();
            if(p.x > 0 && p.x < 6 && p.y > 0 && p.y < 6 && board.get(p.x).get(p.y) instanceof OdkrytePole && !board.get(x).get(y).isOpened)
            {
                buttons.get(p.x).get(p.y).setImageResource(board.get(p.x).get(p.y).action());
                board.get(x).get(y).isOpened = true;
                floodQ.add(new Point(p.x - 1, p.y));
                floodQ.add(new Point(p.x + 1, p.y));
                floodQ.add(new Point(p.x, p.y - 1));
                floodQ.add(new Point(p.x, p.y + 1));
            }
        }
        */
        if ( x >= 0 && x <6 && y >= 0 && y < 6 && board.get(x).get(y) instanceof OdkrytePole && !board.get(x).get(y).isOpened)
        {
            buttons.get(x).get(y).setImageResource(board.get(x).get(y).action());
            board.get(x).get(y).isOpened = true;
            floodFill(x - 1, y);
            floodFill(x + 1, y);
            floodFill(x, y - 1);
            floodFill(x, y + 1);
        }
        else{
                return;
        }
    }

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

        int bombCount = 0;
        for(int i=0; i<6; i++) {
            for(int j=0; j<6; j++) {
                bombCount = 0;
                if(board.get(i).get(j).getClass() != Bomba.class) {
                    try {
                        if (board.get(i - 1).get(j - 1) instanceof Bomba) bombCount++;
                    } catch (Exception e){}
                    try{
                       if (board.get(i).get(j - 1) instanceof Bomba) bombCount++;
                    } catch (Exception ex) {}
                    try {
                        if (board.get(i - 1).get(j) instanceof Bomba) bombCount++;
                    } catch (Exception ex) {}
                    try {
                        if (board.get(i + 1).get(j) instanceof Bomba) bombCount++;
                    } catch (Exception ex) {}
                    try {
                        if (board.get(i - 1).get(j + 1) instanceof Bomba) bombCount++;
                    } catch (Exception ex) {}
                    try {
                        if (board.get(i).get(j + 1) instanceof Bomba) bombCount++;
                    } catch (Exception ex) {}
                    try {
                        if (board.get(i + 1).get(j + 1) instanceof Bomba) bombCount++;
                    } catch (Exception ex) {}
                    try {
                        if (board.get(i + 1).get(j - 1) instanceof Bomba) bombCount++;
                    } catch (Exception ex) {}

                    switch (bombCount) {
                        case 1:
                            board.get(i).set(j, (new Pole1()));
                            break;
                        case 2:
                            board.get(i).set(j, (new Pole2()));
                            break;
                        case 3:
                            board.get(i).set(j, (new Pole3()));
                            break;
                        case 4:
                            board.get(i).set(j, (new Pole4()));
                            break;
                        case 5:
                            board.get(i).set(j, (new Pole5()));
                            break;
                        case 6:
                            board.get(i).set(j, (new Pole6()));
                            break;
                        case 7:
                            board.get(i).set(j, (new Pole7()));
                            break;
                        case 8:
                            board.get(i).set(j, (new Pole8()));
                            break;
                    }
                }
            }
        }


        LinearLayout layout = (LinearLayout) findViewById(R.id.game_layout);

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
                buttons.get(x).get(j).setOnLongClickListener(this);
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

        if (board.get(x).get(y) instanceof Bomba){
            wincond--;
            Intent intentend = new Intent(getApplicationContext(), EndActivity.class);
            intentend.putExtra("wincond",wincond);
            intentend.putExtra("count",count);
            startActivity(intentend);
        }
        wincond++;
        if(wincond>=30)
        {

            Intent intentend = new Intent(getApplicationContext(), EndActivity.class);
            intentend.putExtra("wincond",wincond);
            intentend.putExtra("count",count);
            startActivity(intentend);

        }

        if(board.get(x).get(y) instanceof OdkrytePole) floodFill(x, y);
        if(!board.get(x).get(y).isOpened) board.get(x).get(y).isOpened = true;

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

    @Override
    public boolean onLongClick(View view) {
        if(count==0)customHandler.postDelayed(updateTimerThread, 0);
        int x = view.getId() / 10;
        int y = view.getId() % 10;
        buttons.get(x).get(y).setImageResource(R.mipmap.flag);
        return true;
    }
}
