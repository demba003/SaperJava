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

import java.text.MessageFormat;

import javalab.pk.saper.Model.Bomb;
import javalab.pk.saper.Model.Board;
import javalab.pk.saper.Model.BlankField;
import javalab.pk.saper.R;
import javalab.pk.saper.View.BoardView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{
    Board board;
    BoardView boardView;

    TextView time;
    int timeCount = 0;

    private Handler customHandler = new Handler();

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeCount++;
            time.setText(MessageFormat.format("{0} {1}", getString(R.string.time), String.valueOf(timeCount)));
            customHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        time = (TextView) findViewById(R.id.time);

        board = Board.get(6, 6, 6);
        boardView = new BoardView(this, board, this, this);
        board.setBoardView(boardView);
    }

    @Override
    public void onClick(View view) {
        if(timeCount ==0) customHandler.postDelayed(updateTimerThread, 0);
        int x = view.getId() / 10;
        int y = view.getId() % 10;

        if(board.getField(x,y) instanceof BlankField) board.floodFill(x, y);
        if(!board.getField(x,y).isOpened) board.open(x, y);

        if (board.getField(x,y) instanceof Bomb || board.getOpened() >= 30){
            if (board.getField(x,y) instanceof Bomb) board.bombSetOpened();
            Intent intentend = new Intent(getApplicationContext(), EndActivity.class);
            intentend.putExtra("wincond", board.getOpened());
            intentend.putExtra("timeCount", timeCount);
            startActivity(intentend);
            finish();
            Board.clear();
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
        if(timeCount ==0) customHandler.postDelayed(updateTimerThread, 0);
        int x = view.getId() / 10;
        int y = view.getId() % 10;
        boardView.markAsBomb(x, y);
        return true;
    }

    @Override
    public void onDestroy(){
        Board.clear();
        super.onDestroy();
    }
}
