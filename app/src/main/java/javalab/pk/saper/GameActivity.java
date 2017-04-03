package javalab.pk.saper;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Vector;

public class GameActivity extends AppCompatActivity {

    Vector<Vector<ImageButton>> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buttons = new Vector<>();

        LinearLayout layout = (LinearLayout) findViewById(R.id.game_layout);

        for (int i = 0; i < 6; i++) {
            LinearLayout row = new LinearLayout(this);
            buttons.add(new Vector<ImageButton>());

            for (int j = 0; j <6; j++) {
                buttons.get(i).add(new ImageButton(this));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.weight = 1;

                buttons.get(i).get(j).setLayoutParams(p);
                buttons.get(i).get(j).setImageResource(R.mipmap.ic_launcher);
                buttons.get(i).get(j).setId(j + 1 + (i * 10));
                row.addView(buttons.get(i).get(j));
            }

            layout.addView(row);
        }

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
